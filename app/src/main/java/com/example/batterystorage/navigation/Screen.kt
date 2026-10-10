package com.example.batterystorage.navigation
sealed class Screen(val route: String) {
    data object MainMenu : Screen("main_menu")
    data object Battery : Screen("battery")
    data object Settings : Screen("settings")
    data object History : Screen("history")
    data object Information : Screen("information")
}