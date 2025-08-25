package dev.korryr.medauth.utils.helpers

// Data persistence helper for theme preference
object TtthemePreferences {
    // You can implement SharedPreferences or DataStore here
    fun saveThemePreference(isDark: Boolean) {
        // Save to SharedPreferences or DataStore
    }
    
    fun getThemePreference(): Boolean {
        // Read from SharedPreferences or DataStore
        return false // Default to light theme
    }
}