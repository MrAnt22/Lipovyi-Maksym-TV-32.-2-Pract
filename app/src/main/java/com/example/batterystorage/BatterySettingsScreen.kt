
package com.example.batterystorage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.batterystorage.data.BatteryValidator
import com.example.batterystorage.viewmodel.BatteryViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BatterySettingsScreen(
    onBackClick: () -> Unit,
    batteryViewModel: BatteryViewModel
) {
    val uiState by batteryViewModel.uiState.collectAsStateWithLifecycle()

    var capacity by remember {
        mutableStateOf(uiState.capacity.toString())
    }
    var power by remember {
        mutableStateOf(uiState.power.toString())
    }
    var batteryLevel by remember {
        mutableStateOf(uiState.batteryLevel.toString())
    }

    var capacityError by remember { mutableStateOf<String?>(null) }
    var powerError by remember { mutableStateOf<String?>(null) }
    var batteryLevelError by remember { mutableStateOf<String?>(null) }

    var submitted by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }
    var resultMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FormHeader()

        BatteryInputField(
            value = capacity,
            onValueChange = {
                capacity = it
                capacityError = if (submitted) {
                    BatteryValidator.validateCapacity(it)
                } else null
                resultMessage = ""
            },
            label = "Ємність батареї (кВт·год)",
            hint = "Введіть значення від 0 до 10000",
            error = capacityError,
            keyboardType = KeyboardType.Decimal,
            enabled = !isLoading
        )

        BatteryInputField(
            value = power,
            onValueChange = {
                power = it
                powerError = if (submitted) {
                    BatteryValidator.validatePower(it)
                } else null
                resultMessage = ""
            },
            label = "Потужність (кВт)",
            hint = "Введіть значення від 0 до 5000",
            error = powerError,
            keyboardType = KeyboardType.Decimal,
            enabled = !isLoading
        )

        BatteryInputField(
            value = batteryLevel,
            onValueChange = {
                batteryLevel = it
                batteryLevelError = if (submitted) {
                    BatteryValidator.validateBatteryLevel(it)
                } else null
                resultMessage = ""
            },
            label = "Початковий заряд (%)",
            hint = "Введіть число від 0 до 100",
            error = batteryLevelError,
            keyboardType = KeyboardType.Number,
            enabled = !isLoading
        )

        Button(
            onClick = {
                submitted = true

                capacityError =
                    BatteryValidator.validateCapacity(capacity)
                powerError =
                    BatteryValidator.validatePower(power)
                batteryLevelError =
                    BatteryValidator.validateBatteryLevel(batteryLevel)

                isSuccess = capacityError == null &&
                        powerError == null &&
                        batteryLevelError == null

                if (isSuccess) {
                    val newCapacity =
                        capacity.replace(',', '.').toDouble()
                    val newPower =
                        power.replace(',', '.').toDouble()
                    val newLevel = batteryLevel.trim().toInt()

                    isLoading = true
                    resultMessage = ""

                    scope.launch {
                        delay(1000)

                        batteryViewModel.updateSettings(
                            capacity = newCapacity,
                            power = newPower,
                            batteryLevel = newLevel
                        )

                        isLoading = false
                        resultMessage = "Налаштування успішно застосовано"
                    }
                } else {
                    resultMessage = "Виправте помилки у формі"
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (isLoading) "Збереження..."
                else "Зберегти налаштування"
            )
        }

        if (isLoading) {
            CircularProgressIndicator()
        }

        if (resultMessage.isNotEmpty()) {
            Text(
                text = resultMessage,
                color = if (isSuccess) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        }

        OutlinedButton(
            onClick = onBackClick,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Повернутися до меню")
        }
    }
}

@Composable
fun FormHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Battery Storage",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Налаштування системи накопичення енергії",
            fontSize = 16.sp
        )
    }
}

@Composable
fun BatteryInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    hint: String,
    error: String?,
    keyboardType: KeyboardType,
    enabled: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = error != null,
        enabled = enabled,
        singleLine = true,
        supportingText = {
            Text(error ?: hint)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
