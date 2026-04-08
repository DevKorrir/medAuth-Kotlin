package dev.korryr.medauth.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import dev.korryr.medauth.data.local.preferences.AppState
import dev.korryr.medauth.data.local.preferences.themePreference.ThemePreferences
import dev.korryr.medauth.data.local.preferences.themePreference.data.state.ThemeState
import dev.korryr.medauth.data.local.preferences.themePreference.viewModel.ThemeViewModel
import dev.korryr.medauth.data.local.preferences.themePreference.viewModel.collectAppAsState
import dev.korryr.medauth.data.local.preferences.themePreference.viewModel.collectThemeAsState
import dev.korryr.medauth.presentation.features.history.HistoryScreen
import dev.korryr.medauth.presentation.features.auth.login.LoginScreen as LoginScreenUI
import dev.korryr.medauth.presentation.features.auth.onboarding.OnboardingScreen as OnboardingScreenUI
import dev.korryr.medauth.presentation.features.auth.proflile.ProfileScreen as ProfileScreenUI
import dev.korryr.medauth.presentation.features.history.HistoryScreen as HistoryScreenUI
import dev.korryr.medauth.presentation.features.home.HomeScreen as HomeScreenUI
import dev.korryr.medauth.presentation.features.report.ReportScreen as ReportScreenUI
import dev.korryr.medauth.presentation.features.scan.ScanScreen as ScanScreenUI
import dev.korryr.medauth.presentation.features.verification.ResultScreen as ResultScreenUI

/**
 * Root navigation host.
 *
 * Back-stack layout
 * ─────────────────
 * Outer stack  : OnboardingScreen → LoginScreen → MainGraph
 *                                                 ↳ ResultDetailsScreen(scanId)
 *                                                    ↳ ReportScreen
 *
 * Inner (tab) stack (owned by MainScreenContainer):
 *   HomeScreen | ScanScreen | HistoryScreen | ProfileScreen
 */
@Composable
fun AppNavigation(
    themePreferences: ThemePreferences,
    modifier: Modifier = Modifier
) {
    // Single ThemeViewModel shared across the whole navigation tree
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val themeState by themeViewModel.collectThemeAsState()
    val appState   by themeViewModel.collectAppAsState()

    val backStack = remember { mutableStateListOf<Any>(OnboardingScreen) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() != null },
        modifier = modifier
    ) { key ->
        when (key) {

            // ── Auth ─────────────────────────────────────────────────────────

            is OnboardingScreen -> NavEntry(key) {
                OnboardingScreenUI(
                    onFinishOnboarding = {
                        backStack.clear()
                        backStack.add(LoginScreen)
                    }
                )
            }

            is LoginScreen -> NavEntry(key) {
                LoginScreenUI(
                    onLoginSuccess = {
                        backStack.clear()
                        backStack.add(MainGraph)
                    },
                    onUseBiometrics = {
                        backStack.clear()
                        backStack.add(MainGraph)
                    }
                )
            }

            // ── Main (tab container) ─────────────────────────────────────────

            is MainGraph -> NavEntry(key) {
                MainScreenContainer(
                    themePreferences = themePreferences,
                    themeState = themeState,
                    appState = appState,
                    onNavigateToResult = { scanId ->
                        backStack.add(ResultDetailsScreen(scanId))
                    }
                )
            }

            // ── Detail screens (float above bottom bar) ──────────────────────

            is ResultDetailsScreen -> NavEntry(key) {
                ResultScreenUI(
                    onNavigateBack = { backStack.removeLastOrNull() },
                    onReportSuspicious = { backStack.add(ReportScreen) }
                )
            }

            is ReportScreen -> NavEntry(key) {
                ReportScreenUI(
                    onNavigateBack = { backStack.removeLastOrNull() }
                )
            }

            // ─────────────────────────────────────────────────────────────────
            else -> NavEntry(key) { Text("Unknown screen") }
        }
    }
}

// ─── Tab container ────────────────────────────────────────────────────────────

/**
 * Hosts the bottom navigation bar and its inner NavDisplay.
 *
 * [onNavigateToResult] bubbles scan results up to the outer back-stack so
 * ResultScreen renders above the bottom bar (no bottom-bar visible on result).
 */
@Composable
fun MainScreenContainer(
    themePreferences: ThemePreferences,
    themeState: ThemeState,
    appState: AppState,
    onNavigateToResult: (String) -> Unit
) {
    // Independent back-stack for the tab area; starts on Scan as the "home" tab.
    val bottomBackStack = remember { mutableStateListOf<Any>(ScanScreen) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(backStack = bottomBackStack)
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = bottomBackStack,
            onBack = { bottomBackStack.removeLastOrNull() != null },
            modifier = Modifier.padding(innerPadding)
        ) { key ->
            when (key) {

                is HomeScreen -> NavEntry(key) {
                    HomeScreenUI(appState = appState)
                }

                is ScanScreen -> NavEntry(key) {
                    ScanScreenUI(
                        // On a successful scan push ResultDetailsScreen to the *outer* stack
                        // so the result screen covers the entire window (no bottom bar).
                        onScanSuccess = { scannedCode ->
                            onNavigateToResult(scannedCode)
                        }
                    )
                }

                is HistoryScreen -> NavEntry(key) {
                    HistoryScreen(
                        // HistoryScreen delivers an Int entity-id; ResultDetailsScreen wants String.
                        onScanClick = { entityId ->
                            onNavigateToResult(entityId.toString())
                        }
                    )
                }

                is ProfileScreen -> NavEntry(key) {
                    ProfileScreenUI(
                        themePreferences = themePreferences,
                        themeState = themeState,
                        appState = appState
                    )
                }

                else -> NavEntry(key) { Text("Unknown tab") }
            }
        }
    }
}

// ─── Bottom navigation bar ────────────────────────────────────────────────────

@Composable
fun BottomNavigationBar(backStack: MutableList<Any>) {
    val currentRoute = backStack.lastOrNull()

    NavigationBar {
        bottomNavigationItems.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                icon  = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        // Flat single-instance tab switching: clear and push.
                        backStack.clear()
                        backStack.add(item.route)
                    }
                }
            )
        }
    }
}
