package com.example.batterystorage.ui.theme

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.batterystorage.data.BatteryCalculator

@Composable
fun BatteryScreen() {

    var batteryLevel by remember {
        mutableStateOf(65)
    }

    var isCharging by remember {
        mutableStateOf(false)
    }

    var isDischarging by remember {
        mutableStateOf(false)
    }

    val calculator = remember {
        BatteryCalculator()
    }

    val animatedLevel by animateFloatAsState(
        targetValue = batteryLevel / 100f,
        label = "batteryLevel"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Advanced режим",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(15.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Рівень заряду",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$batteryLevel%",
                    fontSize = 36.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(animatedLevel)
                            .height(35.dp)
                            .background(
                                if (batteryLevel > 30) {
                                    Color(0xFF4CAF50)
                                } else {
                                    Color(0xFFF44336)
                                }
                            )
                    ) {}
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = calculator.getBatteryMessage(batteryLevel)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Card(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text("Ємність")
                    Text("100 кВт·год")
                }
            }

            Card(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text("Потужність")
                    Text("25 кВт")
                }
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = calculator.getBatteryStatus(
                isCharging = isCharging,
                isDischarging = isDischarging
            ),
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Button(
                onClick = {
                    batteryLevel =
                        calculator.chargeBattery(batteryLevel)

                    isCharging = true
                    isDischarging = false
                }
            ) {
                Text("Зарядити")
            }

            Button(
                onClick = {
                    batteryLevel =
                        calculator.dischargeBattery(batteryLevel)

                    isCharging = false
                    isDischarging = true
                }
            ) {
                Text("Розрядити")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                batteryLevel = calculator.resetBattery()
                isCharging = false
                isDischarging = false
            }
        ) {
            Text("Скинути")
        }
    }
}
