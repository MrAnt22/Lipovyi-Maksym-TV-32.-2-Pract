
package com.example.batterystorage.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.batterystorage.BatterySettingsScreen
import com.example.batterystorage.MainMenuScreen
import com.example.batterystorage.ui.theme.BatteryStorageScreen
import com.example.batterystorage.viewmodel.BatteryViewModel
import com.example.batterystorage.BatteryHistoryScreen
import com.example.batterystorage.BatteryInformationScreen

@Composable
fun AppNavigation(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val batteryViewModel: BatteryViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.MainMenu.route
    ) {
        composable(Screen.MainMenu.route) {
            MainMenuScreen(
                onBatteryClick = {
                    navController.navigate(Screen.Battery.route)
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onHistoryClick = {
                    navController.navigate(Screen.History.route)
                },
                onInformationClick = {
                    navController.navigate(Screen.Information.route)
                },
                onLogout = onLogout
            )
        }

        composable(Screen.Battery.route) {
            Column {
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Повернутися до меню")
                }

                BatteryStorageScreen(
                    onLogout = onLogout,
                    batteryViewModel = batteryViewModel
                )
            }
        }

        composable(Screen.Settings.route) {
            BatterySettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                batteryViewModel = batteryViewModel
            )
        }

        composable(Screen.History.route) {
            BatteryHistoryScreen(
                batteryViewModel = batteryViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Information.route) {
                BatteryInformationScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
    }
}

@Composable
private fun NavigationPlaceholder(
    title: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title)

        Button(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Повернутися до меню")
        }
    }
}
