package com.example.batterystorage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.batterystorage.navigation.AppNavigation
import com.example.batterystorage.ui.theme.BatteryStorageTheme
import com.example.batterystorage.ui.theme.LoginScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BatteryStorageTheme {

                var isLoggedIn by remember {
                    mutableStateOf(false)
                }

                if (isLoggedIn) {
                    AppNavigation(
                        onLogout = {
                            isLoggedIn = false
                        }
                    )
                } else {
                    LoginScreen(
                        onLoginSuccess = {
                            isLoggedIn = true
                        }
                    )
                }
            }
        }
    }
}