package dev.korryr.medauth.data.local.preferences.themePreference.thememanager

import dev.korryr.medauth.data.local.preferences.themePreference.ThemePreferences
import dev.korryr.medauth.data.local.preferences.themePreference.data.state.ThemeState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(
    private val themePreferences: ThemePreferences
) {
    /**
     * Get the theme state flow for reactive UI updates
     */
    val themeStateFlow: Flow<ThemeState> = themePreferences.themePreferencesFlow

    /**
     * Toggle dark theme preference
     */
    suspend fun toggleDarkTheme() {
        val currentState = themePreferences.getCurrentThemeState()
        themePreferences.updateThemePreference(!currentState.isDarkTheme)
    }

    /**
     * Toggle dynamic color preference
     */
    suspend fun toggleDynamicColor() {
        val currentState = themePreferences.getCurrentThemeState()
        themePreferences.updateDynamicColorPreference(!currentState.isDynamicColor)
    }

    /**
     * Toggle auto theme preference
     */
    suspend fun toggleAutoTheme() {
        val currentState = themePreferences.getCurrentThemeState()
        themePreferences.updateAutoThemePreference(!currentState.isAutoTheme)
    }

    /**
     * Set theme preference explicitly
     */
    suspend fun setDarkTheme(isDark: Boolean) {
        themePreferences.updateThemePreference(isDark)
    }

    /**
     * Complete first launch setup
     */
    suspend fun completeFirstLaunch() {
        themePreferences.updateFirstLaunchPreference(false)
    }

    /**
     * Initialize default preferences on first launch
     */
    suspend fun initializeDefaultPreferences() {
        val currentState = themePreferences.getCurrentThemeState()
        if (currentState.isFirstLaunch) {
            themePreferences.updateThemeState(
                currentState.copy(
                    isDynamicColor = true,
                    isAutoTheme = true,
                    isFirstLaunch = false
                )
            )
        }
    }
}

// Extension functions for easier usage
suspend fun ThemePreferences.toggleDarkMode() {
    val current = getCurrentThemeState()
    updateThemePreference(!current.isDarkTheme)
}

suspend fun ThemePreferences.toggleDynamicColors() {
    val current = getCurrentThemeState()
    updateDynamicColorPreference(!current.isDynamicColor)
}

suspend fun ThemePreferences.toggleAutoTheme() {
    val current = getCurrentThemeState()
    updateAutoThemePreference(!current.isAutoTheme)
}