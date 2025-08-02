package dev.korryr.medauth.data.local.preferences.themePreference.data.state

// Theme state data class with defaults
data class ThemeState(
    val isDarkTheme: Boolean = false,
    val isDynamicColor: Boolean = true,
    val isAutoTheme: Boolean = true,
    val isFirstLaunch: Boolean = true
)