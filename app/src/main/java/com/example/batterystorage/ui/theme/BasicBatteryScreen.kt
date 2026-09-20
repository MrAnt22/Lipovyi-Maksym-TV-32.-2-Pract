package com.example.batterystorage.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BatteryStorageScreen(
    onLogout: () -> Unit
) {

    var selectedMode by remember {
        mutableStateOf("Advanced")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Система накопичення енергії"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                onClick = {
                    selectedMode = "Basic"
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Basic")
            }

            Button(
                onClick = {
                    selectedMode = "Medium"
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Medium")
            }

            Button(
                onClick = {
                    selectedMode = "Advanced"
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Advanced")
            }
        }

        when (selectedMode) {

            "Basic" -> {
                BasicBatteryApp()
            }

            "Medium" -> {
                MediumBatteryApp()
            }

            "Advanced" -> {
                BatteryScreen()
            }
        }

        Button(
            onClick = onLogout,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Вийти")
        }
    }
}