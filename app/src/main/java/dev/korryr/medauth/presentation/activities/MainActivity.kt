package dev.korryr.medauth.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dev.korryr.medauth.data.local.preferences.ThemePreferences
import dev.korryr.medauth.data.local.preferences.ThemeState
import dev.korryr.medauth.navigation.AppNavigation
import dev.korryr.medauth.presentation.ui.theme.MedAuthTheme

class MainActivity : ComponentActivity() {

    private lateinit var themePreferences: ThemePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize theme preferences
        themePreferences = ThemePreferences(this)

        setContent {
            val navController = rememberNavController()

            // Theme state management
            val systemDarkTheme = isSystemInDarkTheme()

            // Collect theme preferences reactively
            val isDarkTheme by themePreferences.isDarkThemeFlow.collectAsState(initial = false)
            val isDynamicColor by themePreferences.isDynamicColorFlow.collectAsState(initial = true)
            val isAutoTheme by themePreferences.isAutoThemeFlow.collectAsState(initial = true)

            // Determine final theme based on preferences
            val finalDarkTheme = if (isAutoTheme) systemDarkTheme else isDarkTheme

            MedAuthTheme(
                darkTheme = finalDarkTheme,
                dynamicColor = isDynamicColor
            ) {
                AppNavigation(
                    themeState = ThemeState(
                        isDarkTheme = isDarkTheme,
                        isDynamicColor = isDynamicColor,
                        isAutoTheme = isAutoTheme
                    ),
                    themePreferences = themePreferences,
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
        }
    }
}
