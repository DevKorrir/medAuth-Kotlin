package dev.korryr.medauth.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String
) {
    object Home : Screen("home_screen")
    object Scan : Screen("scan_screen")
    object Verify : Screen("verify_screen")
    object History : Screen("history_screen")
    object Profile : Screen("profile_screen")
    object Result : Screen("result_screen")

}

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : BottomNavItem(
        route = Screen.Home.route,
        icon = Icons.Default.Home,
        label = "Home"
    )

    object Scan : BottomNavItem(
        route = Screen.Scan.route,
        icon = Icons.Default.Camera,
        label = "Scan"
    )

    object Verify : BottomNavItem(
        route = Screen.Verify.route,
        icon = Icons.Default.Check,
        label = "Verify"
    )

    object History : BottomNavItem(
        route = Screen.History.route,
        icon = Icons.Default.History,
        label = "History"
    )

    object Profile : BottomNavItem(
        route = Screen.Profile.route,
        icon = Icons.Default.Person,
        label = "Profile"
    )
}