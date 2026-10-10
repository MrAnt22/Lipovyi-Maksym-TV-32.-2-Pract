package com.example.batterystorage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.batterystorage.database.BatteryEntity
import com.example.batterystorage.viewmodel.BatteryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BatteryHistoryScreen(
    batteryViewModel: BatteryViewModel,
    onBackClick: () -> Unit
) {
    val history by batteryViewModel.batteryHistory
        .collectAsStateWithLifecycle(initialValue = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Історія показників",
            style = MaterialTheme.typography.headlineSmall
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("Назад")
            }

            Button(
                onClick = {
                    batteryViewModel.clearHistory()
                },
                enabled = history.isNotEmpty(),
                modifier = Modifier.weight(1f)
            ) {
                Text("Очистити")
            }
        }

        if (history.isEmpty()) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Історія показників порожня",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.weight(1f))
        } else {
            Text(
                text = "Кількість записів: ${history.size}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(
                    items = history,
                    key = { it.id }
                ) { battery ->
                    BatteryHistoryItem(battery)
                }
            }
        }
    }
}

@Composable
fun BatteryHistoryItem(battery: BatteryEntity) {
    val dateFormat = SimpleDateFormat(
        "dd.MM.yyyy HH:mm:ss",
        Locale.getDefault()
    )

    val formattedDate = dateFormat.format(
        Date(battery.timestamp)
    )

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Рівень заряду: ${battery.batteryLevel}%",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Ємність: ${battery.capacity} кВт·год"
            )

            Text(
                text = "Потужність: ${battery.power} кВт"
            )

            Text(
                text = "Стан: ${battery.status}"
            )
        }
    }
}
