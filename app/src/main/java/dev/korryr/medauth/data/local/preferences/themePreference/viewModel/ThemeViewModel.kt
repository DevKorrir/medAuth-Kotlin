package dev.korryr.medauth.data.local.preferences.themePreference.viewModel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.korryr.medauth.data.local.preferences.AppPreferences
import dev.korryr.medauth.data.local.preferences.AppState
import dev.korryr.medauth.data.local.preferences.themePreference.ThemePreferences
import dev.korryr.medauth.data.local.preferences.themePreference.data.state.ThemeState
import dev.korryr.medauth.data.local.preferences.themePreference.thememanager.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeManager: ThemeManager,
    private val themePreferences: ThemePreferences,
    private val appPreferences: AppPreferences
) : ViewModel() {

    // Loading state for splash screen
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Theme state from DataStore
    val themeState: StateFlow<ThemeState> = themePreferences.themePreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeState()
        )

    // App preferences state from DataStore
    val appState: StateFlow<AppState> = appPreferences.appPreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppState()
        )

    // Combined state for complex UI logic
    val combinedState: StateFlow<CombinedState> = combine(
        themeState,
        appState
    ) { theme, app ->
        CombinedState(
            themeState = theme,
            appState = app
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CombinedState()
    )

    // Error state
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    init {
        // Initialize and load preferences
        initializePreferences()
    }

    /**
     * Initialize preferences and complete loading
     */
    fun initializePreferences() {
        viewModelScope.launch {
            try {
                // Initialize default preferences on first launch
                themeManager.initializeDefaultPreferences()
                
                // Mark loading as complete
                _isLoading.value = false
            } catch (e: Exception) {
                _errorState.value = "Failed to initialize preferences: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    // Theme-related functions
    fun toggleDarkTheme() {
        viewModelScope.launch {
            try {
                themeManager.toggleDarkTheme()
                logEvent("theme_toggled", mapOf("is_dark" to themeState.value.isDarkTheme))
            } catch (e: Exception) {
                _errorState.value = "Failed to toggle dark theme: ${e.message}"
            }
        }
    }

    fun toggleAutoTheme() {
        viewModelScope.launch {
            try {
                themeManager.toggleAutoTheme()
                logEvent("auto_theme_toggled", mapOf("is_auto" to themeState.value.isAutoTheme))
            } catch (e: Exception) {
                _errorState.value = "Failed to toggle auto theme: ${e.message}"
            }
        }
    }

    fun toggleDynamicColor() {
        viewModelScope.launch {
            try {
                themeManager.toggleDynamicColor()
                logEvent("dynamic_color_toggled", mapOf("is_dynamic" to themeState.value.isDynamicColor))
            } catch (e: Exception) {
                _errorState.value = "Failed to toggle dynamic color: ${e.message}"
            }
        }
    }

    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            try {
                themeManager.setDarkTheme(isDark)
            } catch (e: Exception) {
                _errorState.value = "Failed to set dark theme: ${e.message}"
            }
        }
    }

    // App preferences functions
    fun toggleNotifications() {
        viewModelScope.launch {
            try {
                val current = appState.value.notificationsEnabled
                appPreferences.updateNotificationsEnabled(!current)
                logEvent("notifications_toggled", mapOf("enabled" to !current))
            } catch (e: Exception) {
                _errorState.value = "Failed to toggle notifications: ${e.message}"
            }
        }
    }

    fun toggleAutoSaveScans() {
        viewModelScope.launch {
            try {
                val current = appState.value.autoSaveScans
                appPreferences.updateAutoSaveScans(!current)
                logEvent("auto_save_toggled", mapOf("enabled" to !current))
            } catch (e: Exception) {
                _errorState.value = "Failed to toggle auto save: ${e.message}"
            }
        }
    }

    fun toggleOfflineMode() {
        viewModelScope.launch {
            try {
                val current = appState.value.offlineMode
                appPreferences.updateOfflineMode(!current)
                logEvent("offline_mode_toggled", mapOf("enabled" to !current))
            } catch (e: Exception) {
                _errorState.value = "Failed to toggle offline mode: ${e.message}"
            }
        }
    }

    fun toggleBiometricAuth() {
        viewModelScope.launch {
            try {
                val current = appState.value.biometricAuth
                appPreferences.updateBiometricAuth(!current)
                logEvent("biometric_auth_toggled", mapOf("enabled" to !current))
            } catch (e: Exception) {
                _errorState.value = "Failed to toggle biometric auth: ${e.message}"
            }
        }
    }

    fun toggleAutoBackup() {
        viewModelScope.launch {
            try {
                val current = appState.value.autoBackup
                appPreferences.updateAutoBackup(!current)
                logEvent("auto_backup_toggled", mapOf("enabled" to !current))
            } catch (e: Exception) {
                _errorState.value = "Failed to toggle auto backup: ${e.message}"
            }
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            try {
                appPreferences.updateSelectedLanguage(language)
                logEvent("language_changed", mapOf("language" to language))
            } catch (e: Exception) {
                _errorState.value = "Failed to update language: ${e.message}"
            }
        }
    }

    // Bulk update functions
    fun updateThemeState(newThemeState: ThemeState) {
        viewModelScope.launch {
            try {
                themePreferences.updateThemeState(newThemeState)
                logEvent("theme_state_updated", mapOf(
                    "is_dark" to newThemeState.isDarkTheme,
                    "is_auto" to newThemeState.isAutoTheme,
                    "is_dynamic" to newThemeState.isDynamicColor
                ))
            } catch (e: Exception) {
                _errorState.value = "Failed to update theme state: ${e.message}"
            }
        }
    }

    fun updateAppState(newAppState: AppState) {
        viewModelScope.launch {
            try {
                appPreferences.updateAppState(newAppState)
                logEvent("app_state_updated", mapOf(
                    "notifications" to newAppState.notificationsEnabled,
                    "auto_save" to newAppState.autoSaveScans,
                    "offline_mode" to newAppState.offlineMode,
                    "biometric_auth" to newAppState.biometricAuth,
                    "auto_backup" to newAppState.autoBackup,
                    "language" to newAppState.selectedLanguage
                ))
            } catch (e: Exception) {
                _errorState.value = "Failed to update app state: ${e.message}"
            }
        }
    }

    // Reset functions
    fun resetThemeToDefaults() {
        viewModelScope.launch {
            try {
                themePreferences.resetToDefaults()
                logEvent("theme_reset_to_defaults")
            } catch (e: Exception) {
                _errorState.value = "Failed to reset theme: ${e.message}"
            }
        }
    }

    fun resetAllPreferences() {
        viewModelScope.launch {
            try {
                themePreferences.resetToDefaults()
                // Reset app preferences to default state
                appPreferences.updateAppState(AppState())
                logEvent("all_preferences_reset")
            } catch (e: Exception) {
                _errorState.value = "Failed to reset preferences: ${e.message}"
            }
        }
    }

    // Error handling
    fun clearError() {
        _errorState.value = null
    }

    // Analytics/Logging function (implement with your analytics solution)
    fun logEvent(eventName: String, parameters: Map<String, Any> = emptyMap()) {
        // TODO: Implement with Firebase Analytics, Crashlytics, or your preferred solution
        // Example:
        // firebaseAnalytics.logEvent(eventName, Bundle().apply {
        //     parameters.forEach { (key, value) ->
        //         when (value) {
        //             is String -> putString(key, value)
        //             is Boolean -> putBoolean(key, value)
        //             is Int -> putInt(key, value)
        //             is Long -> putLong(key, value)
        //             is Double -> putDouble(key, value)
        //         }
        //     }
        // })
        
        // For now, just log to console in debug mode
//        if (BuildConfig.DEBUG) {
//            println("Event: $eventName, Parameters: $parameters")
//        }
    }

    // Profile-specific functions
    fun updateUserProfile(name: String, email: String) {
        viewModelScope.launch {
            try {
                // TODO: Implement user profile update logic
                logEvent("profile_updated", mapOf(
                    "has_name" to name.isNotEmpty(),
                    "has_email" to email.isNotEmpty()
                ))
            } catch (e: Exception) {
                _errorState.value = "Failed to update profile: ${e.message}"
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                // TODO: Implement sign out logic
                // Clear sensitive data
                resetAllPreferences()
                logEvent("user_signed_out")
            } catch (e: Exception) {
                _errorState.value = "Failed to sign out: ${e.message}"
            }
        }
    }

    // Medicine-specific functions
    fun reportFakeMedicine(medicineId: String, reason: String) {
        viewModelScope.launch {
            try {
                // TODO: Implement fake medicine reporting logic
                logEvent("fake_medicine_reported", mapOf(
                    "medicine_id" to medicineId,
                    "reason" to reason
                ))
            } catch (e: Exception) {
                _errorState.value = "Failed to report fake medicine: ${e.message}"
            }
        }
    }

    fun deleteHistoryItem(historyId: String) {
        viewModelScope.launch {
            try {
                // TODO: Implement history deletion logic
                logEvent("history_item_deleted", mapOf("history_id" to historyId))
            } catch (e: Exception) {
                _errorState.value = "Failed to delete history item: ${e.message}"
            }
        }
    }

    // Utility functions
    fun refreshPreferences() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                // Force refresh by re-reading from DataStore
                val currentTheme = themePreferences.getCurrentThemeState()
                val currentApp = appPreferences.getCurrentAppState()
                _isLoading.value = false
                logEvent("preferences_refreshed")
            } catch (e: Exception) {
                _errorState.value = "Failed to refresh preferences: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Clean up any resources if needed
        logEvent("theme_viewmodel_cleared")
    }
}

// Combined state for complex UI scenarios
data class CombinedState(
    val themeState: ThemeState = ThemeState(),
    val appState: AppState = AppState()
) {
    // Computed properties for convenience
    val shouldShowDynamicColorOption: Boolean
        get() = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S

    val isFullyConfigured: Boolean
        get() = !themeState.isFirstLaunch

    val hasAdvancedFeaturesEnabled: Boolean
        get() = appState.biometricAuth && appState.autoBackup

    val isMedicalModeEnabled: Boolean
        get() = appState.offlineMode && appState.autoSaveScans
}

// Extension functions for ViewModel usage in Composables
@Composable
fun ThemeViewModel.collectThemeAsState() = themeState.collectAsStateWithLifecycle()

@Composable
fun ThemeViewModel.collectAppAsState() = appState.collectAsStateWithLifecycle()

@Composable
fun ThemeViewModel.collectCombinedAsState() = combinedState.collectAsStateWithLifecycle()

@Composable
fun ThemeViewModel.collectLoadingAsState() = isLoading.collectAsStateWithLifecycle()

@Composable
fun ThemeViewModel.collectErrorAsState() = errorState.collectAsStateWithLifecycle()

// Helper composable for error handling
@Composable
fun ErrorSnackbar(
    errorMessage: String?,
    onDismiss: () -> Unit
) {
    errorMessage?.let { message ->
        LaunchedEffect(message) {
            // Show snackbar or handle error display
            onDismiss()
        }
    }
}