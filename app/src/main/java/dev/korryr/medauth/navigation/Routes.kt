package dev.korryr.medauth.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

// ─── Auth Graph ───────────────────────────────────────────────────────────────
@Serializable data object AuthGraph
@Serializable data object OnboardingScreen
@Serializable data object LoginScreen

// ─── Main Graph ───────────────────────────────────────────────────────────────
@Serializable data object MainGraph

// Bottom-nav tabs
@Serializable data object HomeScreen
@Serializable data object ScanScreen
@Serializable data object HistoryScreen
@Serializable data object ProfileScreen

// Detail screens (rendered on the outer / root back-stack, above the bottom bar)
@Serializable data object ReportScreen

@Serializable
data class ResultDetailsScreen(val scanId: String)

// ─── Bottom-nav items ─────────────────────────────────────────────────────────
data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: Any
)

val bottomNavigationItems = listOf(
    BottomNavItem(
        title = "Home",
        icon = Icons.Default.Home,
        route = HomeScreen
    ),
    BottomNavItem(
        title = "Scan",
        icon = Icons.Default.QrCodeScanner,
        route = ScanScreen
    ),
    BottomNavItem(
        title = "History",
        icon = Icons.Default.History,
        route = HistoryScreen
    ),
    BottomNavItem(
        title = "Settings",
        icon = Icons.Default.Settings,
        route = ProfileScreen
    )
)
