package dev.korryr.medauth.data.local.preferences.themePreference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.korryr.medauth.data.local.preferences.themePreference.data.state.ThemeState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// DataStore instance
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "medauth_theme_preferences"
)

@Singleton
class ThemePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        private val IS_DYNAMIC_COLOR = booleanPreferencesKey("is_dynamic_color")
        private val IS_AUTO_THEME = booleanPreferencesKey("is_auto_theme")
        private val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    // Flow-based reactive preferences
    val themePreferencesFlow: Flow<ThemeState> = dataStore.data
        .catch { exception ->
            // Handle any errors and emit empty preferences
            emit(emptyPreferences())
        }
        .map { preferences ->
            ThemeState(
                isDarkTheme = preferences[IS_DARK_THEME] ?: false,
                isDynamicColor = preferences[IS_DYNAMIC_COLOR] ?: true,
                isAutoTheme = preferences[IS_AUTO_THEME] ?: true,
                isFirstLaunch = preferences[IS_FIRST_LAUNCH] ?: true
            )
        }

    // Individual preference flows for granular reactivity
    val isDarkThemeFlow: Flow<Boolean> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences -> preferences[IS_DARK_THEME] ?: false }

    val isDynamicColorFlow: Flow<Boolean> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences -> preferences[IS_DYNAMIC_COLOR] ?: true }

    val isAutoThemeFlow: Flow<Boolean> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences -> preferences[IS_AUTO_THEME] ?: true }

    val isFirstLaunchFlow: Flow<Boolean> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences -> preferences[IS_FIRST_LAUNCH] ?: true }

    // Suspend functions for one-time operations
    suspend fun updateThemePreference(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDark
        }
    }

    suspend fun updateDynamicColorPreference(isDynamic: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DYNAMIC_COLOR] = isDynamic
        }
    }

    suspend fun updateAutoThemePreference(isAuto: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_AUTO_THEME] = isAuto
        }
    }

    suspend fun updateFirstLaunchPreference(isFirstLaunch: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    // Update multiple preferences atomically
    suspend fun updateThemeState(themeState: ThemeState) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = themeState.isDarkTheme
            preferences[IS_DYNAMIC_COLOR] = themeState.isDynamicColor
            preferences[IS_AUTO_THEME] = themeState.isAutoTheme
            preferences[IS_FIRST_LAUNCH] = themeState.isFirstLaunch
        }
    }

    // Get current state synchronously (use sparingly)
    suspend fun getCurrentThemeState(): ThemeState {
        return themePreferencesFlow.first()
    }

    // Reset all preferences to defaults
    suspend fun resetToDefaults() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}