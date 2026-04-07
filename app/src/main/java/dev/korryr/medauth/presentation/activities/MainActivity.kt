package dev.korryr.medauth.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.korryr.medauth.core.designsystem.theme.MedAuthTheme
import dev.korryr.medauth.data.local.preferences.themePreference.ThemePreferences
import dev.korryr.medauth.data.local.preferences.themePreference.viewModel.ThemeViewModel
import dev.korryr.medauth.navigation.AppNavigation


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var themePreferences: ThemePreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize theme preferences
        themePreferences = ThemePreferences(this)

        setContent {
            MedAuthApp(themePreferences = themePreferences)
        }
    }
}

@Composable
fun MedAuthApp(
    themePreferences: ThemePreferences,
    themeViewModel: ThemeViewModel = hiltViewModel()
){
    // Collect theme state reactively
    val systemDarkTheme = isSystemInDarkTheme()

    // Collect theme preferences reactively
    val isDarkTheme by themePreferences.isDarkThemeFlow.collectAsState(initial = false)
    val isDynamicColor by themePreferences.isDynamicColorFlow.collectAsState(initial = true)
    val isAutoTheme by themePreferences.isAutoThemeFlow.collectAsState(initial = true)

    // Determine final theme based on preferences
    val finalDarkTheme = if (isAutoTheme) systemDarkTheme else isDarkTheme

    // Show loading or main content
    MedAuthTheme(
        darkTheme = finalDarkTheme,
        dynamicColor = isDynamicColor
    ) {
        AppNavigation(
            modifier = Modifier.fillMaxSize()
        )
    }
}

