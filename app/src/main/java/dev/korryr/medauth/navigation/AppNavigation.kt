package dev.korryr.medauth.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.korryr.medauth.data.local.preferences.AppState
import dev.korryr.medauth.data.local.preferences.themePreference.ThemePreferences
import dev.korryr.medauth.data.local.preferences.themePreference.data.state.ThemeState
import dev.korryr.medauth.data.local.preferences.themePreference.viewModel.ThemeViewModel
import dev.korryr.medauth.presentation.features.auth.proflile.ProfileScreen
import dev.korryr.medauth.presentation.features.history.HistoryScreen
import dev.korryr.medauth.presentation.features.home.HomeScreen
import dev.korryr.medauth.presentation.features.scan.ScanScreen
import dev.korryr.medauth.presentation.features.verification.VerifyScreen

@Composable
fun AppNavigation(
    themeState: ThemeState,
    themePreferences: ThemePreferences,
    appState: AppState,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        },
        content = { padding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = modifier.padding(padding)
            ) {
                composable(Screen.Home.route) {
                    HomeScreen()
                }
                composable(Screen.Scan.route) {
                    ScanScreen()
                }
                composable(Screen.Verify.route) {
                    VerifyScreen()
                }
                composable(Screen.History.route) {
                    HistoryScreen()
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        themeState = themeState,
                        themePreferences = themePreferences,
                        appState = appState,
                        modifier = modifier,
                    )
                }
                composable(Screen.Result.route) {
                    //ResultScreen()
                }


            }
        }

    )

}