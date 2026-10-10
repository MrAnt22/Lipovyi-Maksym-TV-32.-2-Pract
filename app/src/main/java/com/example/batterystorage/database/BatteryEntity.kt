package com.example.batterystorage.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battery_history")
data class BatteryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val batteryLevel: Int,
    val capacity: Double,
    val power: Double,
    val status: String,
    val timestamp: Long = System.currentTimeMillis()
)
