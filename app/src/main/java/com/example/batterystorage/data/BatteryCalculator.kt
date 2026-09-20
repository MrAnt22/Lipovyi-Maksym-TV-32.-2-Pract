package com.example.batterystorage.data

class BatteryCalculator {

    fun chargeBattery(currentLevel: Int): Int {
        return (currentLevel + 10).coerceAtMost(100)
    }

    fun dischargeBattery(currentLevel: Int): Int {
        return (currentLevel - 10).coerceAtLeast(0)
    }

    fun resetBattery(): Int {
        return 65
    }

    fun getBatteryStatus(
        isCharging: Boolean,
        isDischarging: Boolean
    ): String {
        return when {
            isCharging -> "Стан: заряджання"
            isDischarging -> "Стан: розряджання"
            else -> "Стан: очікування"
        }
    }

    fun getBatteryMessage(level: Int): String {
        return when {
            level >= 80 -> "Високий рівень заряду"
            level >= 50 -> "Нормальний рівень заряду"
            level >= 20 -> "Низький рівень заряду"
            else -> "Критично низький рівень заряду"
        }
    }
}