package com.example.data.sync

import android.content.Context
import android.util.Log
import com.example.core.PerformanceEngine
import com.example.data.database.BookingRequestDao
import com.example.data.database.SavedEstimateDao
import com.example.data.model.BookingRequest
import com.example.data.model.DetailedProjectBOQ
import com.example.data.model.PoolShape
import com.example.data.model.PoolType
import com.example.data.model.SavedEstimate
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class SyncStatus {
    object Idle : SyncStatus()
    object Syncing : SyncStatus()
    data class Connected(val lastSyncMs: Long, val syncedCount: Int) : SyncStatus()
    data class OfflineLocal(val reason: String) : SyncStatus()
    data class Error(val message: String) : SyncStatus()
}

data class SyncTelemetry(
    val status: SyncStatus = SyncStatus.Idle,
    val isFirestoreAvailable: Boolean = false,
    val totalLeadsSynced: Int = 0,
    val totalEstimatesSynced: Int = 0,
    val lastSyncTimestamp: Long = 0L,
    val statusBadge: String = "⚡ Room Offline Active"
)

/**
 * Enterprise Room-to-Firestore Sync Manager
 * Synchronizes local Room database records (Estimates & Customer Leads) bi-directionally with
 * Cloud Firestore, and streams live BOQ calculation telemetry for real-time CRM updates.
 *
 * Designed with defensive fallbacks: operates seamlessly offline via Room if Firebase is not yet
 * provisioned, and seamlessly transitions to live cloud sync as soon as credentials are detected.
 */
class RoomToFirestoreSyncManager(
    private val context: Context,
    private val savedEstimateDao: SavedEstimateDao,
    private val bookingRequestDao: BookingRequestDao,
    private val scope: CoroutineScope
) {
    private val tag = "RoomFirestoreSync"

    private var firestore: FirebaseFirestore? = null
    private var estimateListenerRegistration: ListenerRegistration? = null
    private var bookingListenerRegistration: ListenerRegistration? = null

    private val _telemetry = MutableStateFlow(SyncTelemetry())
    val telemetry: StateFlow<SyncTelemetry> = _telemetry.asStateFlow()

    init {
        initFirestore()
        startRoomObservability()
    }

    private fun initFirestore() {
        try {
            val app = if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
            } else {
                FirebaseApp.getInstance()
            }

            if (app != null) {
                val db = FirebaseFirestore.getInstance(app)
                firestore = db
                _telemetry.value = _telemetry.value.copy(
                    isFirestoreAvailable = true,
                    statusBadge = "☁️ Firestore Connected · Two-Way Sync Active",
                    status = SyncStatus.Connected(System.currentTimeMillis(), 0)
                )
                PerformanceEngine.recordVolatileEvent("☁️ Firestore connection initialized successfully")
                setupInboundFirestoreListeners(db)
            } else {
                markOfflineFallback("Firebase credentials not configured yet. Operating in local Room DB mode.")
            }
        } catch (e: Exception) {
            Log.w(tag, "Firestore initialization notice (safe fallback): ${e.message}")
            markOfflineFallback(e.message ?: "Local Room mode active")
        }
    }

    private fun markOfflineFallback(reason: String) {
        firestore = null
        _telemetry.value = _telemetry.value.copy(
            isFirestoreAvailable = false,
            statusBadge = "📦 Room DB Active (Offline Protected)",
            status = SyncStatus.OfflineLocal(reason)
        )
    }

    /**
     * Observes local Room SQLite tables and synchronizes new/updated items to Firestore.
     */
    private fun startRoomObservability() {
        // Observe Room Saved Estimates
        scope.launch(Dispatchers.IO) {
            savedEstimateDao.getAllEstimates().collect { estimates ->
                if (firestore != null && estimates.isNotEmpty()) {
                    pushEstimatesToFirestore(estimates)
                }
            }
        }

        // Observe Room Booking Requests (Customer Leads)
        scope.launch(Dispatchers.IO) {
            bookingRequestDao.getAllBookings().collect { bookings ->
                if (firestore != null && bookings.isNotEmpty()) {
                    pushBookingsToFirestore(bookings)
                }
            }
        }
    }

    /**
     * Sets up real-time Firestore snapshot listeners to pull inbound cloud updates into Room.
     */
    private fun setupInboundFirestoreListeners(db: FirebaseFirestore) {
        // Inbound Customer Leads Listener
        bookingListenerRegistration?.remove()
        bookingListenerRegistration = db.collection("customer_leads")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Log.w(tag, "Inbound booking listener error: ${error.message}")
                    return@addSnapshotListener
                }

                if (snapshots != null && !snapshots.isEmpty) {
                    scope.launch(Dispatchers.IO) {
                        try {
                            val localBookings = bookingRequestDao.getAllBookings().firstOrNull() ?: emptyList()
                            val localIds = localBookings.map { it.id }.toSet()

                            for (doc in snapshots.documents) {
                                val id = doc.getLong("id") ?: continue
                                if (!localIds.contains(id)) {
                                    val newBooking = BookingRequest(
                                        id = id,
                                        clientName = doc.getString("clientName") ?: "Client",
                                        phone = doc.getString("phone") ?: "",
                                        suburb = doc.getString("suburb") ?: "Harare",
                                        poolType = doc.getString("poolType") ?: "Gunite",
                                        poolDimensions = doc.getString("poolDimensions") ?: "5x3m",
                                        surveyType = doc.getString("surveyType") ?: "Site Survey & 3D Concept Sketch (US$50)",
                                        preferredDate = doc.getString("preferredDate") ?: "Immediate",
                                        status = doc.getString("status") ?: "Submitted",
                                        notes = doc.getString("notes") ?: "",
                                        timestamp = doc.getLong("timestamp") ?: System.currentTimeMillis()
                                    )
                                    bookingRequestDao.insertBooking(newBooking)
                                    PerformanceEngine.recordVolatileEvent("📥 Synced inbound lead from Firestore: ${newBooking.clientName}")
                                }
                            }
                        } catch (e: Exception) {
                            Log.e(tag, "Failed to apply inbound leads into Room: ${e.message}")
                        }
                    }
                }
            }

        // Inbound Saved Estimates Listener
        estimateListenerRegistration?.remove()
        estimateListenerRegistration = db.collection("saved_estimates")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Log.w(tag, "Inbound estimate listener error: ${error.message}")
                    return@addSnapshotListener
                }

                if (snapshots != null && !snapshots.isEmpty) {
                    scope.launch(Dispatchers.IO) {
                        try {
                            val localEstimates = savedEstimateDao.getAllEstimates().firstOrNull() ?: emptyList()
                            val localIds = localEstimates.map { it.id }.toSet()

                            for (doc in snapshots.documents) {
                                val id = doc.getLong("id") ?: continue
                                if (!localIds.contains(id)) {
                                    val newEstimate = SavedEstimate(
                                        id = id,
                                        poolType = doc.getString("poolType") ?: "Gunite",
                                        shape = doc.getString("shape") ?: "Rectangular",
                                        length = doc.getDouble("length") ?: 5.0,
                                        width = doc.getDouble("width") ?: 3.0,
                                        priceUsd = doc.getDouble("priceUsd") ?: 6500.0,
                                        marketPriceUsd = doc.getDouble("marketPriceUsd") ?: 9500.0,
                                        savingsUsd = doc.getDouble("savingsUsd") ?: 3000.0,
                                        savingsPct = doc.getLong("savingsPct")?.toInt() ?: 32,
                                        dateCreated = doc.getLong("dateCreated") ?: System.currentTimeMillis(),
                                        notes = doc.getString("notes") ?: ""
                                    )
                                    savedEstimateDao.insertEstimate(newEstimate)
                                    PerformanceEngine.recordVolatileEvent("📥 Synced inbound estimate from Firestore: ${newEstimate.shape} ${newEstimate.length}x${newEstimate.width}m")
                                }
                            }
                        } catch (e: Exception) {
                            Log.e(tag, "Failed to apply inbound estimates into Room: ${e.message}")
                        }
                    }
                }
            }
    }

    /**
     * Synchronizes a specific customer lead immediately to Firestore
     */
    suspend fun syncLeadNow(booking: BookingRequest): Boolean = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext false
        try {
            val data = hashMapOf(
                "id" to booking.id,
                "clientName" to booking.clientName,
                "phone" to booking.phone,
                "suburb" to booking.suburb,
                "poolType" to booking.poolType,
                "poolDimensions" to booking.poolDimensions,
                "surveyType" to booking.surveyType,
                "preferredDate" to booking.preferredDate,
                "status" to booking.status,
                "notes" to booking.notes,
                "timestamp" to booking.timestamp,
                "syncedAt" to System.currentTimeMillis(),
                "devicePlatform" to "Android",
                "appVersion" to "2.4.0"
            )

            db.collection("customer_leads")
                .document("lead_${booking.id}")
                .set(data, SetOptions.merge())

            _telemetry.value = _telemetry.value.copy(
                totalLeadsSynced = _telemetry.value.totalLeadsSynced + 1,
                lastSyncTimestamp = System.currentTimeMillis(),
                status = SyncStatus.Connected(System.currentTimeMillis(), _telemetry.value.totalLeadsSynced + 1)
            )
            PerformanceEngine.recordVolatileEvent("☁️ Uploaded customer lead to Firestore: ${booking.clientName}")
            true
        } catch (e: Exception) {
            Log.e(tag, "Error syncing lead to Firestore: ${e.message}")
            false
        }
    }

    /**
     * Synchronizes a specific saved estimate immediately to Firestore
     */
    suspend fun syncEstimateNow(estimate: SavedEstimate): Boolean = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext false
        try {
            val data = hashMapOf(
                "id" to estimate.id,
                "poolType" to estimate.poolType,
                "shape" to estimate.shape,
                "length" to estimate.length,
                "width" to estimate.width,
                "priceUsd" to estimate.priceUsd,
                "marketPriceUsd" to estimate.marketPriceUsd,
                "savingsUsd" to estimate.savingsUsd,
                "savingsPct" to estimate.savingsPct,
                "dateCreated" to estimate.dateCreated,
                "notes" to estimate.notes,
                "syncedAt" to System.currentTimeMillis()
            )

            db.collection("saved_estimates")
                .document("estimate_${estimate.id}")
                .set(data, SetOptions.merge())

            _telemetry.value = _telemetry.value.copy(
                totalEstimatesSynced = _telemetry.value.totalEstimatesSynced + 1,
                lastSyncTimestamp = System.currentTimeMillis(),
                status = SyncStatus.Connected(System.currentTimeMillis(), _telemetry.value.totalEstimatesSynced + 1)
            )
            PerformanceEngine.recordVolatileEvent("☁️ Uploaded estimate to Firestore: ${estimate.length}x${estimate.width}m")
            true
        } catch (e: Exception) {
            Log.e(tag, "Error syncing estimate to Firestore: ${e.message}")
            false
        }
    }

    /**
     * Publishes real-time BOQ calculation updates to Firestore
     * Allows admin control room and sales engineers to see active inquiry calculations live.
     */
    fun syncLiveBOQSession(
        length: Double,
        width: Double,
        poolType: PoolType,
        shape: PoolShape,
        boq: DetailedProjectBOQ,
        suburbName: String
    ) {
        val db = firestore ?: return
        scope.launch(Dispatchers.IO) {
            try {
                val sessionData = hashMapOf(
                    "sessionId" to "active_estimator_session",
                    "timestamp" to System.currentTimeMillis(),
                    "dimensions" to "${length}m x ${width}m",
                    "surfaceAreaM2" to boq.surfaceAreaM2,
                    "volumeLiters" to boq.volumeLiters,
                    "poolType" to poolType.displayName,
                    "shape" to shape.displayName,
                    "suburbName" to suburbName,
                    "totalBaseCostUsd" to boq.totalBaseCostUsd,
                    "grossProfitUsd" to boq.grossProfitUsd,
                    "clientQuotationUsd" to boq.clientQuotationUsd,
                    "clientSavingsUsd" to boq.clientSavingsUsd,
                    "competitorMarketPriceUsd" to boq.typicalMarketCompetitorPriceUsd,
                    "materialsCount" to boq.materials.size,
                    "workersCount" to boq.laborAllocations.sumOf { it.numberOfWorkers },
                    "machineryCount" to boq.machineryMobilizations.size
                )

                db.collection("live_boq_sessions")
                    .document("live_mobile_user")
                    .set(sessionData, SetOptions.merge())
            } catch (e: Exception) {
                // Volatile session update silently ignored if network is unavailable
            }
        }
    }

    private suspend fun pushEstimatesToFirestore(estimates: List<SavedEstimate>) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            val batch = db.batch()
            for (est in estimates) {
                val ref = db.collection("saved_estimates").document("estimate_${est.id}")
                val data = hashMapOf(
                    "id" to est.id,
                    "poolType" to est.poolType,
                    "shape" to est.shape,
                    "length" to est.length,
                    "width" to est.width,
                    "priceUsd" to est.priceUsd,
                    "marketPriceUsd" to est.marketPriceUsd,
                    "savingsUsd" to est.savingsUsd,
                    "savingsPct" to est.savingsPct,
                    "dateCreated" to est.dateCreated,
                    "notes" to est.notes,
                    "syncedAt" to System.currentTimeMillis()
                )
                batch.set(ref, data, SetOptions.merge())
            }
            batch.commit()
            _telemetry.value = _telemetry.value.copy(
                totalEstimatesSynced = estimates.size,
                lastSyncTimestamp = System.currentTimeMillis()
            )
        } catch (e: Exception) {
            Log.w(tag, "Batch estimate push notice: ${e.message}")
        }
    }

    private suspend fun pushBookingsToFirestore(bookings: List<BookingRequest>) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        try {
            val batch = db.batch()
            for (booking in bookings) {
                val ref = db.collection("customer_leads").document("lead_${booking.id}")
                val data = hashMapOf(
                    "id" to booking.id,
                    "clientName" to booking.clientName,
                    "phone" to booking.phone,
                    "suburb" to booking.suburb,
                    "poolType" to booking.poolType,
                    "poolDimensions" to booking.poolDimensions,
                    "surveyType" to booking.surveyType,
                    "preferredDate" to booking.preferredDate,
                    "status" to booking.status,
                    "notes" to booking.notes,
                    "timestamp" to booking.timestamp,
                    "syncedAt" to System.currentTimeMillis()
                )
                batch.set(ref, data, SetOptions.merge())
            }
            batch.commit()
            _telemetry.value = _telemetry.value.copy(
                totalLeadsSynced = bookings.size,
                lastSyncTimestamp = System.currentTimeMillis()
            )
        } catch (e: Exception) {
            Log.w(tag, "Batch booking push notice: ${e.message}")
        }
    }

    /**
     * Manual Trigger to retry/resync immediately
     */
    fun triggerManualSync() {
        scope.launch(Dispatchers.IO) {
            _telemetry.value = _telemetry.value.copy(status = SyncStatus.Syncing)
            initFirestore()
            val estimates = savedEstimateDao.getAllEstimates().firstOrNull() ?: emptyList()
            val bookings = bookingRequestDao.getAllBookings().firstOrNull() ?: emptyList()

            if (firestore != null) {
                pushEstimatesToFirestore(estimates)
                pushBookingsToFirestore(bookings)
                _telemetry.value = _telemetry.value.copy(
                    status = SyncStatus.Connected(System.currentTimeMillis(), estimates.size + bookings.size),
                    statusBadge = "☁️ Firestore Synced (${estimates.size + bookings.size} records)"
                )
                PerformanceEngine.recordVolatileEvent("🔄 Manual Firestore sync completed (${estimates.size + bookings.size} records)")
            } else {
                markOfflineFallback("Offline Room Active · ${estimates.size + bookings.size} local records safe")
            }
        }
    }

    fun cleanUp() {
        estimateListenerRegistration?.remove()
        bookingListenerRegistration?.remove()
    }
}
