package com.example.batterystorage.repository

import com.example.batterystorage.database.BatteryDao
import com.example.batterystorage.database.BatteryEntity
import kotlinx.coroutines.flow.Flow

class BatteryRepository(
    private val batteryDao: BatteryDao
) {

    val batteryHistory: Flow<List<BatteryEntity>> =
        batteryDao.getAllHistory()

    suspend fun saveBatteryState(
        batteryLevel: Int,
        capacity: Double,
        power: Double,
        status: String
    ) {
        val battery = BatteryEntity(
            batteryLevel = batteryLevel,
            capacity = capacity,
            power = power,
            status = status
        )

        batteryDao.insertBattery(battery)
    }

    suspend fun getLastBatteryState(): BatteryEntity? {
        return batteryDao.getLastBattery()
    }

    suspend fun clearHistory() {
        batteryDao.deleteAllHistory()
    }
}
