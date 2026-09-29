package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.BookingRequest
import com.example.data.model.SavedEstimate
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedEstimateDao {
    @Query("SELECT * FROM saved_estimates ORDER BY dateCreated DESC")
    fun getAllEstimates(): Flow<List<SavedEstimate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstimate(estimate: SavedEstimate): Long

    @Delete
    suspend fun deleteEstimate(estimate: SavedEstimate)

    @Query("DELETE FROM saved_estimates WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface BookingRequestDao {
    @Query("SELECT * FROM booking_requests ORDER BY timestamp DESC")
    fun getAllBookings(): Flow<List<BookingRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingRequest): Long

    @Query("UPDATE booking_requests SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)

    @Delete
    suspend fun deleteBooking(booking: BookingRequest)

    @Query("DELETE FROM booking_requests")
    suspend fun clearAllBookings()
}
