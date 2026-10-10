package com.example.batterystorage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BatteryDao {

    @Insert
    suspend fun insertBattery(battery: BatteryEntity)

    @Query("SELECT * FROM battery_history ORDER BY timestamp DESC, id DESC")
    fun getAllHistory(): Flow<List<BatteryEntity>>

    @Query("SELECT * FROM battery_history ORDER BY timestamp DESC, id DESC LIMIT 1")
    suspend fun getLastBattery(): BatteryEntity?

    @Query("DELETE FROM battery_history")
    suspend fun deleteAllHistory()
}
