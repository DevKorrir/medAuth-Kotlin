package dev.korryr.medauth.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
//import javax.inject.Inject
//import javax.inject.Singleton

// DataStore extension
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "medauth_preferences")

@Singleton
class ThemePreferences @Inject constructor(
    private val context: Context
) {
    // DataStore keys
    private object PreferencesKeys {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        val IS_DYNAMIC_COLOR = booleanPreferencesKey("is_dynamic_color")
        val IS_AUTO_THEME = booleanPreferencesKey("is_auto_theme") // Follow system theme
    }

    // DataStore implementation (Recommended)

    /**
     * Save theme preference using DataStore
     */
    suspend fun saveThemePreference(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DARK_THEME] = isDark
        }
    }

    /**
     * Save dynamic color preference
     */
    suspend fun saveDynamicColorPreference(isDynamic: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_DYNAMIC_COLOR] = isDynamic
        }
    }

    /**
     * Save auto theme preference (follow system)
     */
    suspend fun saveAutoThemePreference(isAuto: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_AUTO_THEME] = isAuto
        }
    }

    /**
     * Get theme preference as Flow for reactive updates
     */
    val isDarkThemeFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.IS_DARK_THEME] ?: false // Default to light theme
    }

    /**
     * Get dynamic color preference as Flow
     */
    val isDynamicColorFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.IS_DYNAMIC_COLOR] ?: true // Default to dynamic colors
    }

    /**
     * Get auto theme preference as Flow
     */
    val isAutoThemeFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.IS_AUTO_THEME] ?: true // Default to follow system
    }

    /**
     * Get theme preference synchronously (for one-time reads)
     */
    suspend fun getThemePreference(): Boolean {
        return context.dataStore.data.first()[PreferencesKeys.IS_DARK_THEME] ?: false
    }

    /**
     * Get dynamic color preference synchronously
     */
    suspend fun getDynamicColorPreference(): Boolean {
        return context.dataStore.data.first()[PreferencesKeys.IS_DYNAMIC_COLOR] ?: true
    }

    /**
     * Get auto theme preference synchronously
     */
    suspend fun getAutoThemePreference(): Boolean {
        return context.dataStore.data.first()[PreferencesKeys.IS_AUTO_THEME] ?: true
    }
}


/////////////////////////////////////////////////////////////////////////////
//// Alternative SharedPreferences implementation
/////////////////////////////////////////////////////////////////////////////
//
//@Singleton
//class ThemePreferencesSharedPref @Inject constructor(
//    private val context: Context
//) {
//    companion object {
//        private const val PREF_NAME = "medauth_theme_prefs"
//        private const val KEY_IS_DARK_THEME = "is_dark_theme"
//        private const val KEY_IS_DYNAMIC_COLOR = "is_dynamic_color"
//        private const val KEY_IS_AUTO_THEME = "is_auto_theme"
//    }
//
//    private val sharedPreferences: SharedPreferences by lazy {
//        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//    }
//
//    /**
//     * Save theme preference using SharedPreferences
//     */
//    fun saveThemePreference(isDark: Boolean) {
//        sharedPreferences.edit()
//            .putBoolean(KEY_IS_DARK_THEME, isDark)
//            .apply()
//    }
//
//    /**
//     * Save dynamic color preference
//     */
//    fun saveDynamicColorPreference(isDynamic: Boolean) {
//        sharedPreferences.edit()
//            .putBoolean(KEY_IS_DYNAMIC_COLOR, isDynamic)
//            .apply()
//    }
//
//    /**
//     * Save auto theme preference
//     */
//    fun saveAutoThemePreference(isAuto: Boolean) {
//        sharedPreferences.edit()
//            .putBoolean(KEY_IS_AUTO_THEME, isAuto)
//            .apply()
//    }
//
//    /**
//     * Get theme preference
//     */
//    fun getThemePreference(): Boolean {
//        return sharedPreferences.getBoolean(KEY_IS_DARK_THEME, false)
//    }
//
//    /**
//     * Get dynamic color preference
//     */
//    fun getDynamicColorPreference(): Boolean {
//        return sharedPreferences.getBoolean(KEY_IS_DYNAMIC_COLOR, true)
//    }
//
//    /**
//     * Get auto theme preference
//     */
//    fun getAutoThemePreference(): Boolean {
//        return sharedPreferences.getBoolean(KEY_IS_AUTO_THEME, true)
//    }
//}
//
//// Theme state management for Compose
//@Singleton
//class ThemeManager @Inject constructor(
//    private val themePreferences: ThemePreferences
//) {
//    /**
//     * Get combined theme state for Compose
//     */
//    suspend fun getThemeState(): ThemeState {
//        return ThemeState(
//            isDarkTheme = themePreferences.getThemePreference(),
//            isDynamicColor = themePreferences.getDynamicColorPreference(),
//            isAutoTheme = themePreferences.getAutoThemePreference()
//        )
//    }
//
//    /**
//     * Update theme state
//     */
//    suspend fun updateThemeState(themeState: ThemeState) {
//        themePreferences.saveThemePreference(themeState.isDarkTheme)
//        themePreferences.saveDynamicColorPreference(themeState.isDynamicColor)
//        themePreferences.saveAutoThemePreference(themeState.isAutoTheme)
//    }
//}
//
//// Data class for theme state
//data class ThemeState(
//    val isDarkTheme: Boolean = false,
//    val isDynamicColor: Boolean = true,
//    val isAutoTheme: Boolean = true
//)

// Dependency Injection Module (if using Hilt)
/*
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideThemePreferences(context: Context): ThemePreferences {
        return ThemePreferences(context)
    }

    @Provides
    @Singleton
    fun provideThemeManager(themePreferences: ThemePreferences): ThemeManager {
        return ThemeManager(themePreferences)
    }
}
*/