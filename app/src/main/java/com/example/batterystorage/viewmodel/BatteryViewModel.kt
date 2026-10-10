
package com.example.batterystorage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.batterystorage.data.BatteryCalculator
import com.example.batterystorage.database.BatteryDatabase
import com.example.batterystorage.database.BatteryEntity
import com.example.batterystorage.repository.BatteryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class BatteryUiState(
    val batteryLevel: Int = 65,
    val capacity: Double = 100.0,
    val power: Double = 25.0,
    val status: String = "Очікування"
)

class BatteryViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val calculator = BatteryCalculator()

    private val database = BatteryDatabase.getDatabase(application)
    private val repository = BatteryRepository(database.batteryDao())

    private val _uiState = MutableStateFlow(BatteryUiState())
    val uiState: StateFlow<BatteryUiState> = _uiState.asStateFlow()

    val batteryHistory = repository.batteryHistory

    init {
        viewModelScope.launch {
            val lastBattery = repository.getLastBatteryState()

            if (lastBattery != null) {
                _uiState.value = BatteryUiState(
                    batteryLevel = lastBattery.batteryLevel,
                    capacity = lastBattery.capacity,
                    power = lastBattery.power,
                    status = lastBattery.status
                )
            }
        }
    }

    private fun saveState(state: BatteryUiState) {
        viewModelScope.launch {
            repository.saveBatteryState(
                batteryLevel = state.batteryLevel,
                capacity = state.capacity,
                power = state.power,
                status = state.status
            )
        }
    }

    fun chargeBattery() {
        val current = _uiState.value

        val updated = current.copy(
            batteryLevel = calculator.chargeBattery(current.batteryLevel),
            status = "Заряджання"
        )

        _uiState.value = updated
        saveState(updated)
    }

    fun dischargeBattery() {
        val current = _uiState.value

        val updated = current.copy(
            batteryLevel = calculator.dischargeBattery(current.batteryLevel),
            status = "Розряджання"
        )

        _uiState.value = updated
        saveState(updated)
    }

    fun resetBattery() {
        val updated = BatteryUiState()

        _uiState.value = updated
        saveState(updated)
    }

    fun updateSettings(
        capacity: Double,
        power: Double,
        batteryLevel: Int
    ) {
        val updated = _uiState.value.copy(
            capacity = capacity,
            power = power,
            batteryLevel = batteryLevel,
            status = "Очікування"
        )

        _uiState.value = updated
        saveState(updated)
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}
