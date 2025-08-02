package dev.korryr.medauth.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.korryr.medauth.data.local.preferences.themePreference.ThemePreferences
import dev.korryr.medauth.data.local.preferences.themePreference.data.state.ThemeState
import dev.korryr.medauth.data.local.preferences.themePreference.viewModel.ThemeViewModel
import dev.korryr.medauth.navigation.AppNavigation
import dev.korryr.medauth.presentation.ui.theme.MedAuthTheme


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
    val context = LocalContext.current
    val navController = rememberNavController()

    // Collect theme state reactively
    val systemDarkTheme = isSystemInDarkTheme()

    // Collect theme preferences reactively
    val isDarkTheme by themePreferences.isDarkThemeFlow.collectAsState(initial = false)
    val isDynamicColor by themePreferences.isDynamicColorFlow.collectAsState(initial = true)
    val isAutoTheme by themePreferences.isAutoThemeFlow.collectAsState(initial = true)
    val appState by themeViewModel.appState.collectAsStateWithLifecycle()

    // Determine final theme based on preferences
    val finalDarkTheme = if (isAutoTheme) systemDarkTheme else isDarkTheme

    // Show loading or main content
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
                appState = appState,
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }
    }

