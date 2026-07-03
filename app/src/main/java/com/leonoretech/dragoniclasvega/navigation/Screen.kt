package com.leonoretech.dragoniclasvegas.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object MainMenu : Screen("main_menu")
    data object MiniGames : Screen("mini_games")
    data object Achievements : Screen("achievements")
    data object Shop : Screen("shop")
    data object Profile : Screen("profile")
    data object Settings : Screen("settings")
    data object About : Screen("about")

    data object DragonRunner : Screen("game_dragon_runner")
    data object NeonMemory : Screen("game_neon_memory")
    data object CyberReflex : Screen("game_cyber_reflex")
    data object DragonDodge : Screen("game_dragon_dodge")
    data object LuckyWheel : Screen("game_lucky_wheel")
}
