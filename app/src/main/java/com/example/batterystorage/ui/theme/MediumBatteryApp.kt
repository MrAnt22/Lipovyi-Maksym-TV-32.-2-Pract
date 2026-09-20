package com.example.batterystorage.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.batterystorage.data.BatteryCalculator

@Composable
fun MediumBatteryApp() {

    var batteryLevel by remember {
        mutableStateOf(65)
    }

    var status by remember {
        mutableStateOf("Очікування")
    }

    val calculator = remember {
        BatteryCalculator()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Середній режим")

                Text(
                    text = "Рівень заряду: $batteryLevel%"
                )

                Text(
                    text = "Стан: $status"
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    batteryLevel =
                        calculator.chargeBattery(batteryLevel)

                    status = "Заряджання"
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Зарядити")
            }

            Button(
                onClick = {
                    batteryLevel =
                        calculator.dischargeBattery(batteryLevel)

                    status = "Розряджання"
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Розрядити")
            }
        }

        Button(
            onClick = {
                batteryLevel = calculator.resetBattery()
                status = "Очікування"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Скинути")
        }
    }
}