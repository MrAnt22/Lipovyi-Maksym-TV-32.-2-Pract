package com.example.batterystorage.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BasicBatteryApp() {

    var message by remember {
        mutableStateOf("Система готова до роботи")
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

                Text(
                    text = "Базовий режим"
                )

                Text(
                    text = "Рівень заряду: 65%"
                )
            }
        }

        Button(
            onClick = {
                message = "Стан системи: заряджання"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Перевірити систему")
        }

        Text(
            text = message
        )
    }
}