package dev.korryr.medauth.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.runtime.NavEntry
import dev.korryr.medauth.presentation.features.auth.login.LoginScreen
import dev.korryr.medauth.presentation.features.auth.onboarding.OnboardingScreen

// Stubs for future features
@Composable fun ScanScreenStub() { Text("Scan") }
@Composable fun HistoryScreenStub() { Text("History") }
@Composable fun ProfileScreenStub() { Text("Profile") }

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    // Navigation 3 uses a SnapshotStateList for the backStack instead of a NavController
    val backStack = remember { mutableStateListOf<Any>(OnboardingScreen) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() != null },
        modifier = modifier
    ) { key ->
        when (key) {
            is OnboardingScreen -> NavEntry(key) {
                OnboardingScreen(
                    onFinishOnboarding = {
                        backStack.clear()
                        backStack.add(LoginScreen)
                    }
                )
            }
            is LoginScreen -> NavEntry(key) {
                LoginScreen(
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
            is MainGraph -> NavEntry(key) {
                MainScreenContainer()
            }
            else -> NavEntry(key) {
                Text("Unknown Screen")
            }
        }
    }
}

@Composable
fun MainScreenContainer() {
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
                is ScanScreen -> NavEntry(key) { ScanScreenStub() }
                is HistoryScreen -> NavEntry(key) { HistoryScreenStub() }
                is ProfileScreen -> NavEntry(key) { ProfileScreenStub() }
                else -> NavEntry(key) { Text("Unknown Tab") }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(backStack: MutableList<Any>) {
    val currentRoute = backStack.lastOrNull()

    NavigationBar {
        bottomNavigationItems.forEach { item ->
            // Match our object instance directly since we use Navigation 3 instances
            val isSelected = currentRoute == item.route
            
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        // Pop behavior for bottom tabs: usually we want a single instance or flat history
                        backStack.clear()
                        backStack.add(item.route)
                    }
                }
            )
        }
    }
}