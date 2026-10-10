package com.example.batterystorage.data

object BatteryValidator {

    fun validateCapacity(value: String): String? {
        val capacity = value.trim().replace(',', '.').toDoubleOrNull()

        return when {
            value.isBlank() -> "Введіть ємність батареї"
            capacity == null || !capacity.isFinite() -> "Введіть коректне число"
            capacity <= 0 -> "Ємність повинна бути більше 0"
            capacity > 10000 -> "Максимальна ємність — 10000 кВт·год"
            else -> null
        }
    }

    fun validatePower(value: String): String? {
        val power = value.trim().replace(',', '.').toDoubleOrNull()

        return when {
            value.isBlank() -> "Введіть потужність батареї"
            power == null || !power.isFinite() -> "Введіть коректне число"
            power <= 0 -> "Потужність повинна бути більше 0"
            power > 5000 -> "Максимальна потужність — 5000 кВт"
            else -> null
        }
    }

    fun validateBatteryLevel(value: String): String? {
        val level = value.trim().toIntOrNull()

        return when {
            value.isBlank() -> "Введіть початковий заряд"
            level == null -> "Введіть ціле число"
            level !in 0..100 -> "Заряд повинен бути від 0 до 100%"
            else -> null
        }
    }
}