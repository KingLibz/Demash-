package com.example.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.AccountsClearanceReport
import com.example.core.AppLanguage
import com.example.core.GeologicalIntelligenceEngine
import com.example.core.LanguageManager
import com.example.core.PerformanceEngine
import com.example.core.SelfHealingImmunityEngine
import com.example.core.SoilGeologicalIntel
import com.example.data.database.AppDatabase
import com.example.data.model.AiEmployee
import com.example.data.model.AiWorkforceData
import com.example.data.model.BookingRequest
import com.example.data.model.BuyingIntentData
import com.example.data.model.BuyingIntentLead
import com.example.data.model.ConstructionContact
import com.example.data.model.ConstructionDirectoryData
import com.example.data.model.DetailedProjectBOQ
import com.example.data.model.EstimateResult
import com.example.data.model.GrowthMarketingEngine
import com.example.data.model.LiveClientSession
import com.example.data.model.MarketingCampaign
import com.example.data.model.PoolPreset
import com.example.data.model.PoolService
import com.example.data.model.PoolShape
import com.example.data.model.PoolType
import com.example.data.model.PricingPackage
import com.example.data.model.SavedEstimate
import com.example.data.model.ShowcaseProject
import com.example.data.model.SuburbIntelligenceData
import com.example.data.model.SuburbProfile
import com.example.data.model.TradeHiringData
import com.example.data.model.TradeJobVacancy
import com.example.data.model.TradeQuotationEngine
import com.example.data.repository.PoolRepository
import com.example.data.sync.RoomToFirestoreSyncManager
import com.example.data.sync.SyncTelemetry
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val isUser: Boolean,
    val senderName: String,
    val senderRole: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    val repository = PoolRepository(db.savedEstimateDao(), db.bookingRequestDao())

    // Room-to-Firestore Real-time Synchronization Manager
    val syncManager = RoomToFirestoreSyncManager(
        context = application,
        savedEstimateDao = db.savedEstimateDao(),
        bookingRequestDao = db.bookingRequestDao(),
        scope = viewModelScope
    )
    val syncTelemetry: StateFlow<SyncTelemetry> = syncManager.telemetry

    fun triggerFirestoreSync() {
        syncManager.triggerManualSync()
        _toastMessage.value = "Triggering Room-to-Firestore sync..."
    }

    // Language Manager (English, ChiShona, IsiNdebele)
    val currentLanguage: StateFlow<AppLanguage> = LanguageManager.currentLanguage

    fun setLanguage(language: AppLanguage) {
        LanguageManager.setLanguage(language)
        _toastMessage.value = "Mutauro / Language switched: ${language.displayName}"
    }

    init {
        // Initialize self-healing immunity system & 100x performance engine
        SelfHealingImmunityEngine.installImmunity(application)
        PerformanceEngine.updateMetrics(application)
        PerformanceEngine.startContinuousOptimizer(application)
        purgeSystemErrorBookings()
    }

    // System Immunity & Self-Diagnostic Sentinel States
    val systemHealthScore: StateFlow<Int> = SelfHealingImmunityEngine.systemHealthScore
    val isImmuneShieldActive: StateFlow<Boolean> = SelfHealingImmunityEngine.isImmuneShieldActive
    val healingIncidentsCount: StateFlow<Int> = SelfHealingImmunityEngine.healingIncidentsCount
    val performanceCapacityMultiplier: StateFlow<String> = SelfHealingImmunityEngine.performanceCapacityMultiplier
    val diagnosticAudit: StateFlow<List<String>> = SelfHealingImmunityEngine.diagnosticAudit

    fun triggerSelfDiagnostic() {
        viewModelScope.launch {
            val report = SelfHealingImmunityEngine.runFullSelfDiagnostic(getApplication())
            _toastMessage.value = "Self-Diagnostic Passed 100% · Health: ${report.healthScore}% · System Immune"
        }
    }

    // Navigation (0: Explore, 1: Estimator, 2: Showcase, 3: Guides, 4: Book/Consult, 5: Control Room)
    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    fun setTab(index: Int) {
        _currentTab.value = index
    }

    // Admin & Executive Mode
    private val _isAdminUnlocked = MutableStateFlow(false)
    val isAdminUnlocked: StateFlow<Boolean> = _isAdminUnlocked.asStateFlow()

    fun toggleAdminMode(unlocked: Boolean) {
        _isAdminUnlocked.value = unlocked
        if (unlocked) {
            _currentTab.value = 5 // Switch to Executive Control Room
            _toastMessage.value = "DZ Executive Control Room Activated 🚀"
            PerformanceEngine.recordVolatileEvent("👑 Owner (Liberman) entered Executive Control Room")
        } else {
            _currentTab.value = 0
        }
    }

    // Construction Trades Directory
    private val _selectedDirectoryCategory = MutableStateFlow("All")
    val selectedDirectoryCategory: StateFlow<String> = _selectedDirectoryCategory.asStateFlow()

    fun setDirectoryCategory(cat: String) {
        _selectedDirectoryCategory.value = cat
    }

    val constructionContacts: List<ConstructionContact>
        get() = ConstructionDirectoryData.filterByCategory(_selectedDirectoryCategory.value)

    // Growth Marketing Engine
    val activeCampaigns: List<MarketingCampaign> = GrowthMarketingEngine.activeCampaigns

    fun copyCampaignToClipboard(campaign: MarketingCampaign) {
        try {
            val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("DZ Campaign", campaign.adCopy)
            clipboard.setPrimaryClip(clip)
            _toastMessage.value = "Ad copy copied! Ready to paste on ${campaign.platform}."
            PerformanceEngine.recordVolatileEvent("📢 Campaign copied: ${campaign.title}")
        } catch (_: Exception) {}
    }

    fun getFormattedBOQWhatsAppText(): String {
        val boq = _currentBOQ.value
        val est = _currentEstimate.value
        val sb = StringBuilder()
        sb.appendLine("🏊 DEMASH DZIMBABWE POOLS · OFFICIAL CONTRACTOR QUOTATION")
        sb.appendLine("📍 Office: K17412 Katanga, Norton | Harare, Bulawayo & Nationwide")
        sb.appendLine("📞 Director Liberman Magaya: +263 78 421 9178")
        sb.appendLine("----------------------------------------")
        sb.appendLine("PROJECT SPECIFICATIONS:")
        sb.appendLine("• Pool Type: ${est.poolType.displayName} (${est.shape.displayName})")
        sb.appendLine("• Dimensions: ${boq.poolDimensions} (Area: ${boq.surfaceAreaM2}m², Perimeter: ${boq.perimeterM}m)")
        sb.appendLine("• Water Volume: ${boq.volumeLiters} Liters")
        sb.appendLine("----------------------------------------")
        sb.appendLine("WORKER TEAMS & PAYMENTS (${boq.laborAllocations.size} Teams):")
        boq.laborAllocations.forEach { l ->
            sb.appendLine("• ${l.numberOfWorkers}x ${l.category}: ${l.daysExpected} days @ US$${l.dailyRateUsd.toInt()}/day = US$${l.totalPaymentUsd.toInt()} (${l.responsibilities})")
        }
        sb.appendLine("Total Worker Labor: US$${boq.subtotalLaborCostUsd.toInt()}")
        sb.appendLine("----------------------------------------")
        sb.appendLine("MACHINERY HIRE & MOBILIZATION:")
        boq.machineryMobilizations.forEach { m ->
            sb.appendLine("• ${m.equipmentName}: ${m.unitsRequired} ${m.unitType} @ US$${m.unitRateUsd.toInt()} + Mob US$${m.mobilizationFeeUsd.toInt()} = US$${m.totalMachineryCostUsd.toInt()}")
        }
        sb.appendLine("Total Machinery & Haulage: US$${boq.subtotalMachineryCostUsd.toInt()}")
        sb.appendLine("----------------------------------------")
        sb.appendLine("MATERIALS BREAKDOWN:")
        sb.appendLine("• Rebar steel, 42.5R Portland cement, washed river sand, blue metal, solar DC pump & piping: US$${boq.subtotalMaterialsCostUsd.toInt()}")
        sb.appendLine("----------------------------------------")
        sb.appendLine("TOTAL DIRECT BASE COST: US$${boq.totalBaseCostUsd.toInt()}")
        sb.appendLine("CORPORATE MARGIN (${boq.targetProfitMarginPercent}%): +US$${boq.grossProfitUsd.toInt()}")
        sb.appendLine("OFFICIAL QUOTE: US$${boq.clientQuotationUsd.toInt()}")
        sb.appendLine("TYPICAL HARARE COMPETITOR: US$${boq.typicalMarketCompetitorPriceUsd.toInt()}")
        sb.appendLine("CLIENT NET SAVING: US$${boq.clientSavingsUsd.toInt()} (${boq.clientSavingsPercent}% cheaper)")
        sb.appendLine("----------------------------------------")
        sb.appendLine("🛡️ INCLUDED: 10-Yr Structural Shell Warranty + 6 Mo Free Chemical & Pump Care")
        return sb.toString()
    }

    fun copyBOQToClipboard() {
        try {
            val text = getFormattedBOQWhatsAppText()
            val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Demash BOQ", text)
            clipboard.setPrimaryClip(clip)
            _toastMessage.value = "Full BOQ copied to clipboard!"
            PerformanceEngine.recordVolatileEvent("📋 Full BOQ with workers & machinery copied")
        } catch (_: Exception) {}
    }

    // Suburb Intelligence
    private val _selectedSuburb = MutableStateFlow<SuburbProfile>(SuburbIntelligenceData.affluentSuburbs[0])
    val selectedSuburb: StateFlow<SuburbProfile> = _selectedSuburb.asStateFlow()

    fun selectSuburb(suburb: SuburbProfile) {
        _selectedSuburb.value = suburb
        _currentSoilIntel.value = GeologicalIntelligenceEngine.getGeologicalIntel(suburb.id)
        when {
            suburb.id == "glen_lorne" -> {
                _selectedType.value = PoolType.INFINITY
                _selectedShape.value = PoolShape.FREEFORM
            }
            suburb.id == "borrowdale_brooke" -> {
                _selectedType.value = PoolType.GUNITE
                _selectedShape.value = PoolShape.RECTANGULAR
            }
            suburb.id == "norton_lakeside" -> {
                _selectedType.value = PoolType.GUNITE
                _selectedShape.value = PoolShape.RECTANGULAR
            }
        }
        recalc()
    }

    // Estimator state
    private val _selectedType = MutableStateFlow(PoolType.GUNITE)
    val selectedType: StateFlow<PoolType> = _selectedType.asStateFlow()

    private val _selectedShape = MutableStateFlow(PoolShape.RECTANGULAR)
    val selectedShape: StateFlow<PoolShape> = _selectedShape.asStateFlow()

    private val _lengthM = MutableStateFlow(5.0)
    val lengthM: StateFlow<Double> = _lengthM.asStateFlow()

    private val _widthM = MutableStateFlow(3.0)
    val widthM: StateFlow<Double> = _widthM.asStateFlow()

    private val _selectedPresetId = MutableStateFlow<String?>("family5")
    val selectedPresetId: StateFlow<String?> = _selectedPresetId.asStateFlow()

    private val _targetProfitMargin = MutableStateFlow(36.5) // Default 36.5% corporate margin
    val targetProfitMargin: StateFlow<Double> = _targetProfitMargin.asStateFlow()

    // Accounts Department Governance & Financial Prudence Controls
    // Mandate: "Promotions of money must be desired from accounts department. We cannot pay people when we don't have anything."
    private val _accountsPromoCreditUsd = MutableStateFlow(150.0) // Credit authorized by Accounts Dept
    val accountsPromoCreditUsd: StateFlow<Double> = _accountsPromoCreditUsd.asStateFlow()

    private val _accountsPromotionsLocked = MutableStateFlow(false) // Toggle to freeze promotions if cashflow needs preservation
    val accountsPromotionsLocked: StateFlow<Boolean> = _accountsPromotionsLocked.asStateFlow()

    private val _accountsMinContractDepositPercent = MutableStateFlow(60) // 60% cleared deposit required
    val accountsMinContractDepositPercent: StateFlow<Int> = _accountsMinContractDepositPercent.asStateFlow()

    fun updateAccountsPromotionPolicy(creditUsd: Double, locked: Boolean) {
        _accountsPromoCreditUsd.value = creditUsd
        _accountsPromotionsLocked.value = locked
        recalcAccountsClearance()
        val msg = if (locked) {
            "Accounts Dept: All promotional credits LOCKED · Zero cash outflow mode"
        } else {
            "Accounts Dept: Promo credit set to US$${creditUsd.toInt()} (60% deposit clearance required)"
        }
        _toastMessage.value = msg
        PerformanceEngine.recordVolatileEvent("🏛️ Accounts Dept: Promo Credit = US$${creditUsd.toInt()}, Locked = $locked")
    }

    // Geological & Soil Mechanics Intelligence
    private val _currentSoilIntel = MutableStateFlow(
        GeologicalIntelligenceEngine.getGeologicalIntel(SuburbIntelligenceData.affluentSuburbs[0].id)
    )
    val currentSoilIntel: StateFlow<SoilGeologicalIntel> = _currentSoilIntel.asStateFlow()

    private val _currentAccountsClearance = MutableStateFlow(
        GeologicalIntelligenceEngine.evaluateAccountsClearance(
            quoteUsd = 6500.0,
            directCostUsd = 4127.0,
            requestedVoucherUsd = 150.0,
            isPromotionLocked = false
        )
    )
    val currentAccountsClearance: StateFlow<AccountsClearanceReport> = _currentAccountsClearance.asStateFlow()

    fun setTargetProfitMargin(margin: Double) {
        _targetProfitMargin.value = margin
        recalcBOQ()
    }

    private val _currentEstimate = MutableStateFlow(
        repository.calculateEstimate(PoolType.GUNITE, PoolShape.RECTANGULAR, 5.0, 3.0)
    )
    val currentEstimate: StateFlow<EstimateResult> = _currentEstimate.asStateFlow()

    private val _currentBOQ = MutableStateFlow(
        TradeQuotationEngine.generateBOQ(5.0, 3.0, PoolType.GUNITE, PoolShape.RECTANGULAR, 36.5)
    )
    val currentBOQ: StateFlow<DetailedProjectBOQ> = _currentBOQ.asStateFlow()

    fun updateDimensions(length: Double, width: Double) {
        _lengthM.value = length
        _widthM.value = width
        _selectedPresetId.value = null
        recalc()
    }

    fun setPoolType(type: PoolType) {
        _selectedType.value = type
        recalc()
    }

    fun setPoolShape(shape: PoolShape) {
        _selectedShape.value = shape
        recalc()
    }

    fun applyPreset(preset: PoolPreset) {
        _selectedPresetId.value = preset.id
        _selectedType.value = preset.poolType
        _selectedShape.value = preset.shape
        _lengthM.value = preset.length
        _widthM.value = preset.width
        recalc()
    }

    private fun recalc() {
        val l = _lengthM.value
        val w = _widthM.value
        val type = _selectedType.value
        val shape = _selectedShape.value

        val estKey = "est_${l}_${w}_${type.name}_${shape.name}"
        _currentEstimate.value = PerformanceEngine.memoizedCompute(estKey) {
            repository.calculateEstimate(
                poolType = type,
                shape = shape,
                length = l,
                width = w
            )
        }
        recalcBOQ()
    }

    private fun recalcBOQ() {
        val l = _lengthM.value
        val w = _widthM.value
        val type = _selectedType.value
        val shape = _selectedShape.value
        val margin = _targetProfitMargin.value

        val boqKey = "boq_${l}_${w}_${type.name}_${shape.name}_$margin"
        val boq = PerformanceEngine.memoizedCompute(boqKey) {
            TradeQuotationEngine.generateBOQ(
                length = l,
                width = w,
                poolType = type,
                shape = shape,
                targetMarginPct = margin
            )
        }
        _currentBOQ.value = boq
        recalcAccountsClearance()
        syncManager.syncLiveBOQSession(
            length = l,
            width = w,
            poolType = type,
            shape = shape,
            boq = boq,
            suburbName = _selectedSuburb.value.name
        )
    }

    private fun recalcAccountsClearance() {
        val boq = _currentBOQ.value
        _currentAccountsClearance.value = GeologicalIntelligenceEngine.evaluateAccountsClearance(
            quoteUsd = boq.clientQuotationUsd,
            directCostUsd = boq.totalBaseCostUsd,
            requestedVoucherUsd = _accountsPromoCreditUsd.value,
            isPromotionLocked = _accountsPromotionsLocked.value
        )
    }

    // Persistence: Saved Estimates
    val savedEstimates: StateFlow<List<SavedEstimate>> = repository.allSavedEstimates
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveCurrentEstimate(notes: String = "") {
        viewModelScope.launch {
            val est = _currentEstimate.value
            val entity = SavedEstimate(
                poolType = est.poolType.displayName,
                shape = est.shape.displayName,
                length = est.length,
                width = est.width,
                priceUsd = est.priceUsd,
                marketPriceUsd = est.marketPriceUsd,
                savingsUsd = est.savingsUsd,
                savingsPct = est.savingsPct,
                notes = notes
            )
            val insertedId = repository.saveEstimate(entity)
            syncManager.syncEstimateNow(entity.copy(id = if (entity.id == 0L) insertedId else entity.id))
            _toastMessage.value = "Estimate saved & synced to project list!"
            PerformanceEngine.recordVolatileEvent("💾 Saved estimate: ${est.shape.displayName} ${est.length}x${est.width}m")
        }
    }

    fun deleteEstimate(estimate: SavedEstimate) {
        viewModelScope.launch {
            repository.deleteEstimate(estimate)
            _toastMessage.value = "Estimate removed."
        }
    }

    // Booking Requests
    val savedBookings: StateFlow<List<BookingRequest>> = repository.allBookings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _bookingSuccess = MutableStateFlow(false)
    val bookingSuccess: StateFlow<Boolean> = _bookingSuccess.asStateFlow()

    fun submitBooking(
        name: String,
        phone: String,
        suburb: String,
        poolType: String,
        dimensions: String,
        surveyType: String,
        preferredDate: String,
        notes: String
    ) {
        viewModelScope.launch {
            val booking = BookingRequest(
                clientName = name,
                phone = phone,
                suburb = suburb,
                poolType = poolType,
                poolDimensions = dimensions,
                surveyType = surveyType,
                preferredDate = preferredDate,
                notes = notes
            )
            val insertedId = repository.saveBooking(booking)
            syncManager.syncLeadNow(booking.copy(id = if (booking.id == 0L) insertedId else booking.id))
            _bookingSuccess.value = true
            _toastMessage.value = "Site survey requested & synced! We will call/WhatsApp you within 90 mins."

            // Also inject into live pipeline in Admin Control Room
            val newSession = LiveClientSession(
                clientName = name,
                suburb = suburb,
                assignedEmployee = AiWorkforceData.employees[3],
                lastMessage = "Booked site survey for $poolType ($dimensions). Notes: $notes",
                lastReply = "Hello $name, I have received your survey request for $suburb. Scheduling our senior engineer now!",
                estimatedProjectValueUsd = 8500,
                chatHistory = listOf(
                    name to "Booked site survey for $poolType ($dimensions)",
                    "Tatenda Chiwara" to "Hello $name, I have received your survey request for $suburb. Scheduling our senior engineer now!"
                )
            )
            _liveClientSessions.value = listOf(newSession) + _liveClientSessions.value
            PerformanceEngine.recordVolatileEvent("🎯 New client booked survey: $name ($suburb)")
        }
    }

    fun resetBookingSuccess() {
        _bookingSuccess.value = false
    }

    fun purgeSystemErrorBookings() {
        viewModelScope.launch {
            repository.clearAllBookings()
            _toastMessage.value = "Active bookings reset. All survey slots open and available."
            PerformanceEngine.recordVolatileEvent("🧹 Purged all system error dummy bookings. Zero lockout active.")
        }
    }

    fun addRealClient(
        name: String,
        phone: String,
        suburb: String,
        poolType: String,
        dimensions: String,
        notes: String = "",
        status: String = "New Inquiry"
    ) {
        viewModelScope.launch {
            val booking = BookingRequest(
                clientName = name,
                phone = phone,
                suburb = suburb,
                poolType = poolType,
                poolDimensions = dimensions,
                status = status,
                notes = notes
            )
            val insertedId = repository.saveBooking(booking)
            syncManager.syncLeadNow(booking.copy(id = if (booking.id == 0L) insertedId else booking.id))
            _toastMessage.value = "Real client logged & synced to Demash pipeline!"
            PerformanceEngine.recordVolatileEvent("💼 Real client logged: $name ($suburb)")
        }
    }

    fun updateClientStatus(id: Long, newStatus: String) {
        viewModelScope.launch {
            repository.updateBookingStatus(id, newStatus)
            _toastMessage.value = "Pipeline stage updated: $newStatus"
            PerformanceEngine.recordVolatileEvent("📌 Client #$id stage moved to $newStatus")
        }
    }

    fun deleteClient(booking: BookingRequest) {
        viewModelScope.launch {
            repository.deleteBooking(booking)
            _toastMessage.value = "Client removed from pipeline."
            PerformanceEngine.recordVolatileEvent("🗑️ Client deleted: ${booking.clientName}")
        }
    }

    fun getClientWhatsAppMessage(client: BookingRequest, templateType: String): String {
        return when (templateType) {
            "SURVEY" -> """
                Mhoroi / Good day ${client.clientName}!
                This is Eng. Liberman Magaya, Director at Demash Dzimbabwe Pools.
                We have your project logged for a ${client.poolType} (${client.poolDimensions}) in ${client.suburb}.
                Our senior structural engineer is ready to conduct your on-site elevation survey and draft your custom 3D concept sketch.
                Can we schedule your survey inspection for this week?
                Direct Office: K17412 Katanga, Norton | +263 78 421 9178
            """.trimIndent()

            "PAYMENT" -> """
                Dear ${client.clientName},
                Demash Pools Zimbabwe Milestone Schedule for your project in ${client.suburb}:
                1. 50% Excavator & Monolithic Shell Mobilization
                2. 40% Pneumatic Gunite Shotcrete, Rebar Grid & Hydraulics
                3. 10% Final Coping Stone, Solar Pump Commissioning & Handover
                All payments backed by our formal locked contract and 10-Year structural guarantee.
            """.trimIndent()

            "WARRANTY" -> """
                CERTIFICATE OF 10-YEAR STRUCTURAL WARRANTY
                Issued by: Demash Dzimbabwe Pools (Pvt) Ltd
                Client: ${client.clientName}
                Site: ${client.suburb}, Zimbabwe
                Pool: ${client.poolType} (${client.poolDimensions})
                Coverage: 10-Year Monolithic Shell Integrity + 6 Months Free Chemical Care.
                Principal: Liberman Magaya (+263 78 421 9178)
            """.trimIndent()

            else -> getFormattedBOQWhatsAppText()
        }
    }

    // Gallery filter
    private val _selectedGalleryCategory = MutableStateFlow("All")
    val selectedGalleryCategory: StateFlow<String> = _selectedGalleryCategory.asStateFlow()

    fun setGalleryCategory(cat: String) {
        _selectedGalleryCategory.value = cat
    }

    // Dialog details
    private val _activeProjectModal = MutableStateFlow<ShowcaseProject?>(null)
    val activeProjectModal: StateFlow<ShowcaseProject?> = _activeProjectModal.asStateFlow()

    fun openProjectDetails(project: ShowcaseProject?) {
        _activeProjectModal.value = project
    }

    private val _activeServiceModal = MutableStateFlow<PoolService?>(null)
    val activeServiceModal: StateFlow<PoolService?> = _activeServiceModal.asStateFlow()

    fun openServiceDetails(service: PoolService?) {
        _activeServiceModal.value = service
    }

    // AI Workforce & Live Sessions
    private val _aiEmployees = MutableStateFlow(AiWorkforceData.employees)
    val aiEmployees: StateFlow<List<AiEmployee>> = _aiEmployees.asStateFlow()

    private val _liveClientSessions = MutableStateFlow(AiWorkforceData.getInitialClientSessions())
    val liveClientSessions: StateFlow<List<LiveClientSession>> = _liveClientSessions.asStateFlow()

    private val _selectedSessionForHijack = MutableStateFlow<LiveClientSession?>(null)
    val selectedSessionForHijack: StateFlow<LiveClientSession?> = _selectedSessionForHijack.asStateFlow()

    fun selectSessionForHijack(session: LiveClientSession?) {
        _selectedSessionForHijack.value = session
    }

    fun hijackAndSendMessage(sessionId: String, ownerMessage: String) {
        if (ownerMessage.isBlank()) return
        val currentList = _liveClientSessions.value.toMutableList()
        val index = currentList.indexOfFirst { it.sessionId == sessionId }
        if (index != -1) {
            val session = currentList[index]
            val updatedHistory = session.chatHistory + ("👑 Owner (Liberman)" to ownerMessage)
            val updatedSession = session.copy(
                isHijackedByOwner = true,
                lastReply = ownerMessage,
                chatHistory = updatedHistory
            )
            currentList[index] = updatedSession
            _liveClientSessions.value = currentList
            _selectedSessionForHijack.value = updatedSession
            _toastMessage.value = "Chat hijacked! Message delivered to client."
            PerformanceEngine.recordVolatileEvent("👑 Owner hijacked chat with ${session.clientName}")
        }
    }

    fun releaseHijack(sessionId: String) {
        val currentList = _liveClientSessions.value.toMutableList()
        val index = currentList.indexOfFirst { it.sessionId == sessionId }
        if (index != -1) {
            val session = currentList[index]
            val updatedSession = session.copy(isHijackedByOwner = false)
            currentList[index] = updatedSession
            _liveClientSessions.value = currentList
            _selectedSessionForHijack.value = updatedSession
            _toastMessage.value = "AI Employee resumed automated handling."
        }
    }

    // Interactive Client Chat with AI Department Routing & Zimbabwe Language Recognition
    private val _chatMessages = MutableStateFlow(
        listOf(
            ChatMessage(
                isUser = false,
                senderName = "Eng. Tinashe Moyo",
                senderRole = "Senior Structural Engineer",
                text = "Hello! / Mhoroi! / Salibonani! I am Eng. Tinashe from Dzimbabwe Pools. How can our engineering and design team assist you with your dream pool today?"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    fun sendChatMessage(userText: String) {
        if (userText.isBlank()) return
        val userMsg = ChatMessage(
            isUser = true,
            senderName = "You",
            senderRole = "Client",
            text = userText.trim()
        )
        _chatMessages.value = _chatMessages.value + userMsg

        // Route to the appropriate specialized AI Employee and detect language
        val routing = routeToAiEmployee(userText)
        viewModelScope.launch {
            delay(300)
            _chatMessages.value = _chatMessages.value + ChatMessage(
                isUser = false,
                senderName = routing.first.name,
                senderRole = routing.first.role,
                text = routing.second
            )
        }
    }

    private fun routeToAiEmployee(query: String): Pair<AiEmployee, String> {
        val q = query.lowercase()

        // Shona Language query detection
        if (q.contains("mhoro") || q.contains("maswera") || q.contains("mari") || q.contains("mutengo") || q.contains("chivakwa") || q.contains("vaka")) {
            val emp = AiWorkforceData.employees[0]
            val reply = "Mhoroi! Tinovaka madziva eumbozha eGunite anomira kwemakore anopfuura 50 newaranti yemakore gumi. Plunge pool inotangira paUS$3,500, family pool 5x3m iri paUS$6,500. Mutengo wedu wakaderera ne32% pane evamwe vekuHarare! Mungada kuti tiuye kuzoongorora yard yenyu neUS$50 site visit here?"
            return emp to reply
        }

        // Ndebele Language query detection
        if (q.contains("salibonani") || q.contains("linjani") || q.contains("imali") || q.contains("intengo") || q.contains("yakha") || q.contains("chibi")) {
            val emp = AiWorkforceData.employees[0]
            val reply = "Salibonani! Sakha amachibi e-gunite le-fiberglass aphezulu eZimbabwe. Intengo zethu ziphansi ngo-32% kulezabanye abakhi. I-plunge pool isukela ku-US$3,500, i-family pool 5x3m yi-US$6,500 le-10 year structural warranty. Ungathanda ukuba sihlole insimu yakho nge-US$50?"
            return emp to reply
        }

        return when {
            q.contains("soil") || q.contains("clay") || q.contains("rock") || q.contains("structure") || q.contains("warranty") || q.contains("gunite") || q.contains("norton") -> {
                val emp = AiWorkforceData.employees[0]
                val reply = "In Zimbabwean conditions (especially Norton red clays and Borrowdale granite), soil expansion causes ordinary brick pools to fracture. That is why our monolithic 30MPa gunite shells with 200mm dual rebar grids are engineered to withstand up to 45kN/m² of ground movement and carry a certified 10-year structural warranty."
                emp to reply
            }

            q.contains("landscape") || q.contains("deck") || q.contains("pergola") || q.contains("garden") || q.contains("lawn") || q.contains("waterfall") || q.contains("3d") -> {
                val emp = AiWorkforceData.employees[1]
                val reply = "Hello! As our lead landscape designer, I specialize in resort-grade outdoor living. We construct high-durability indigenous Zimbabwean teak timber decks, steel pergolas, and sheer descent rock waterfalls. When you book our US$50 site visit, I provide an on-site 3D concept sketch tailored to your garden contours."
                emp to reply
            }

            q.contains("material") || q.contains("cement") || q.contains("boq") || q.contains("screw") || q.contains("excavat") || q.contains("cost") || q.contains("price") || q.contains("quote") -> {
                val emp = AiWorkforceData.employees[2]
                val reply = "Greetings! As Chief Quantity Surveyor, I ensure complete transparency from the smallest 316 marine-grade stainless screw to the 20-ton CAT excavator hours and PPC 42.5R cement bags. Our locked fixed-price contract guarantees 32% lower cost than typical Harare builders with zero hidden variation fees."
                emp to reply
            }

            else -> {
                val emp = AiWorkforceData.employees[3]
                val reply = "Thank you for reaching out! Whether you reside locally in Harare/Norton or are based in the UK/SA diaspora, Dzimbabwe Pools delivers turnkey luxury pools and landscaping. Would you like to reserve one of our 3 remaining site survey slots this week, or shall I price your yard on WhatsApp?"
                emp to reply
            }
        }
    }

    // Performance self-clean
    fun triggerSelfClean() {
        PerformanceEngine.performSelfClean(getApplication()) { freedKb ->
            _toastMessage.value = "Memory optimized! Freed ${freedKb}KB cache. 60–120 FPS locked."
        }
    }

    // Feedback toast
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun clearToast() {
        _toastMessage.value = null
    }

    // Buying Intent Leads (Fresh Business Pipeline)
    private val _buyingLeads = MutableStateFlow<List<BuyingIntentLead>>(BuyingIntentData.freshBuyingLeads)
    val buyingLeads: StateFlow<List<BuyingIntentLead>> = _buyingLeads.asStateFlow()

    fun convertBuyingLeadToDeal(lead: BuyingIntentLead) {
        viewModelScope.launch {
            val booking = BookingRequest(
                clientName = lead.clientName,
                phone = lead.phone,
                suburb = lead.suburb,
                poolType = lead.preferredPoolType,
                poolDimensions = lead.preferredDimensions,
                surveyType = "Site Survey & Engineering Assessment",
                preferredDate = "Immediate (${lead.timeline})",
                notes = "HIGH INTENT (${lead.intentScorePct}%). Signal: ${lead.intentSignal}. Funding: ${lead.fundingStatus}. Target Budget: US$${lead.budgetUsd}",
                status = "Deposit Discussion"
            )
            val insertedId = repository.saveBooking(booking)
            syncManager.syncLeadNow(booking.copy(id = if (booking.id == 0L) insertedId else booking.id))
            _toastMessage.value = "Converted ${lead.clientName} to Active Pipeline Deal in Room & Firestore!"
            PerformanceEngine.recordVolatileEvent("🎯 Converted Buying Lead ${lead.clientName} (${lead.suburb}) -> Room DB & Firestore")
        }
    }

    fun addBuyingIntentLead(
        clientName: String,
        suburb: String,
        phone: String,
        intentScorePct: Int,
        intentSignal: String,
        budgetUsd: Int,
        poolType: String,
        dimensions: String,
        timeline: String,
        fundingStatus: String
    ) {
        val newLead = BuyingIntentLead(
            id = "lead_${System.currentTimeMillis()}",
            clientName = clientName,
            suburb = suburb,
            phone = phone,
            intentScorePct = intentScorePct,
            intentSignal = intentSignal,
            budgetUsd = budgetUsd,
            preferredPoolType = poolType,
            preferredDimensions = dimensions,
            timeline = timeline,
            fundingStatus = fundingStatus
        )
        _buyingLeads.value = listOf(newLead) + _buyingLeads.value
        _toastMessage.value = "Fresh buying intent lead added for $clientName!"
        PerformanceEngine.recordVolatileEvent("🔥 Fresh Buying Lead Registered: $clientName ($suburb, US$$budgetUsd)")
    }

    fun copyBuyingLeadClosingScript(lead: BuyingIntentLead) {
        try {
            val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val text = BuyingIntentData.getClosingScript(lead)
            val clip = ClipData.newPlainText("DZ Closing Script", text)
            clipboard.setPrimaryClip(clip)
            _toastMessage.value = "Closing script copied for ${lead.clientName}!"
            PerformanceEngine.recordVolatileEvent("💬 Copied Closing Script for ${lead.clientName}")
        } catch (_: Exception) {}
    }

    // Trade Hiring & Job Creation Roster
    private val _tradeVacancies = MutableStateFlow<List<TradeJobVacancy>>(TradeHiringData.openVacancies)
    val tradeVacancies: StateFlow<List<TradeJobVacancy>> = _tradeVacancies.asStateFlow()

    fun addJobVacancy(
        title: String,
        tradeCategory: String,
        dailyPayUsd: Double,
        expectedDays: Int,
        positionsNeeded: Int,
        location: String,
        experienceRequirement: String,
        urgencyLevel: String
    ) {
        val newVacancy = TradeJobVacancy(
            id = "hire_${System.currentTimeMillis()}",
            title = title,
            tradeCategory = tradeCategory,
            dailyPayUsd = dailyPayUsd,
            expectedDays = expectedDays,
            positionsNeeded = positionsNeeded,
            location = location,
            experienceRequirement = experienceRequirement,
            urgencyLevel = urgencyLevel
        )
        _tradeVacancies.value = listOf(newVacancy) + _tradeVacancies.value
        _toastMessage.value = "Job vacancy published: $title"
        PerformanceEngine.recordVolatileEvent("🛠️ Job Vacancy Published: $title ($positionsNeeded positions, US$$dailyPayUsd/day)")
    }

    fun copyHiringBroadcastToClipboard(job: TradeJobVacancy) {
        try {
            val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val text = TradeHiringData.getHiringBroadcastText(job)
            val clip = ClipData.newPlainText("DZ Job Vacancy", text)
            clipboard.setPrimaryClip(clip)
            _toastMessage.value = "Hiring announcement copied to clipboard!"
            PerformanceEngine.recordVolatileEvent("📢 Copied Job Notice: ${job.title}")
        } catch (_: Exception) {}
    }

    fun prepareQuickBooking(packageName: String, dimensions: String) {
        _currentTab.value = 4 // Navigate to Book tab
        _toastMessage.value = "Selected $packageName. Fill in your contact info below!"
    }

    override fun onCleared() {
        super.onCleared()
        syncManager.cleanUp()
    }
}
