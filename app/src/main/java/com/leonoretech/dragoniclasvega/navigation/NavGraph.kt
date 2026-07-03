package com.leonoretech.dragoniclasvegas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leonoretech.dragoniclasvegas.navigation.Screen
import com.leonoretech.dragoniclasvegas.ui.minigames.CyberReflexScreen
import com.leonoretech.dragoniclasvegas.ui.minigames.DragonDodgeScreen
import com.leonoretech.dragoniclasvegas.ui.minigames.DragonRunnerScreen
import com.leonoretech.dragoniclasvegas.ui.minigames.LuckyWheelScreen
import com.leonoretech.dragoniclasvegas.ui.minigames.NeonMemoryScreen
import com.leonoretech.dragoniclasvegas.ui.screens.AboutScreen
import com.leonoretech.dragoniclasvegas.ui.screens.AchievementsScreen
import com.leonoretech.dragoniclasvegas.ui.screens.LoginScreen
import com.leonoretech.dragoniclasvegas.ui.screens.MainMenuScreen
import com.leonoretech.dragoniclasvegas.ui.screens.MiniGamesScreen
import com.leonoretech.dragoniclasvegas.ui.screens.ProfileScreen
import com.leonoretech.dragoniclasvegas.ui.screens.SettingsScreen
import com.leonoretech.dragoniclasvegas.ui.screens.ShopScreen
import com.leonoretech.dragoniclasvegas.ui.screens.SplashScreen
import com.leonoretech.dragoniclasvegas.viewmodel.PlayerViewModel

@Composable
fun DragonicNavGraph() {
    val navController: NavHostController = rememberNavController()
    val playerViewModel: PlayerViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(onFinished = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = playerViewModel,
                onLoggedIn = {
                    navController.navigate(Screen.MainMenu.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.MainMenu.route) {
            MainMenuScreen(
                viewModel = playerViewModel,
                onPlay = { navController.navigate(Screen.MiniGames.route) },
                onAchievements = { navController.navigate(Screen.Achievements.route) },
                onShop = { navController.navigate(Screen.Shop.route) },
                onProfile = { navController.navigate(Screen.Profile.route) },
                onSettings = { navController.navigate(Screen.Settings.route) },
                onAbout = { navController.navigate(Screen.About.route) }
            )
        }
        composable(Screen.MiniGames.route) {
            MiniGamesScreen(
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable(Screen.Achievements.route) {
            AchievementsScreen(viewModel = playerViewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Shop.route) {
            ShopScreen(viewModel = playerViewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Profile.route) {
            ProfileScreen(viewModel = playerViewModel, onBack = { navController.popBackStack() })
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                viewModel = playerViewModel,
                onBack = { navController.popBackStack() },
                onAbout = { navController.navigate(Screen.About.route) }
            )
        }
        composable(Screen.About.route) {
            AboutScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.DragonRunner.route) {
            DragonRunnerScreen(viewModel = playerViewModel, onExit = { navController.popBackStack() })
        }
        composable(Screen.NeonMemory.route) {
            NeonMemoryScreen(viewModel = playerViewModel, onExit = { navController.popBackStack() })
        }
        composable(Screen.CyberReflex.route) {
            CyberReflexScreen(viewModel = playerViewModel, onExit = { navController.popBackStack() })
        }
        composable(Screen.DragonDodge.route) {
            DragonDodgeScreen(viewModel = playerViewModel, onExit = { navController.popBackStack() })
        }
        composable(Screen.LuckyWheel.route) {
            LuckyWheelScreen(viewModel = playerViewModel, onExit = { navController.popBackStack() })
        }
    }
}
