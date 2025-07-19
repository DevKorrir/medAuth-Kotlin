package dev.korryr.medauth.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavDestination.Companion.hierarchy

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val bottomScreens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Scan,
        BottomNavItem.Verify,
        BottomNavItem.History,
        BottomNavItem.Profile
    )

    val showBottomBar = setOf(
        Screen.Home.route,
        Screen.Scan.route,
        Screen.Verify.route,
        Screen.History.route,
        Screen.Profile.route
    )

    val shouldShowBottomBar = remember(currentRoute) {
        currentRoute?.let { it in showBottomBar } ?: false
    }


    AnimatedVisibility(
        visible = shouldShowBottomBar,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn() ,
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            bottomScreens.forEach { screen ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(screen.route) { }
                    },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.label,
                            tint = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    label = {
                        Text(
                            text = screen.label,
                            color = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }


    }
}

