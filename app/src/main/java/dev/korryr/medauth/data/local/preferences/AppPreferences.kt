package dev.korryr.medauth.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        private val AUTO_SAVE_SCANS = booleanPreferencesKey("auto_save_scans")
        private val OFFLINE_MODE = booleanPreferencesKey("offline_mode")
        private val BIOMETRIC_AUTH = booleanPreferencesKey("biometric_auth")
        private val AUTO_BACKUP = booleanPreferencesKey("auto_backup")
        private val SELECTED_LANGUAGE = androidx.datastore.preferences.core.stringPreferencesKey("selected_language")
    }

    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "medauth_app_preferences"
    )

    private val dataStore: DataStore<Preferences> = context.appDataStore

    val appPreferencesFlow: Flow<AppState> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            AppState(
                notificationsEnabled = preferences[NOTIFICATIONS_ENABLED] ?: true,
                autoSaveScans = preferences[AUTO_SAVE_SCANS] ?: true,
                offlineMode = preferences[OFFLINE_MODE] ?: false,
                biometricAuth = preferences[BIOMETRIC_AUTH] ?: false,
                autoBackup = preferences[AUTO_BACKUP] ?: true,
                selectedLanguage = preferences[SELECTED_LANGUAGE] ?: "en"
            )
        }

    suspend fun updateNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun updateAutoSaveScans(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[AUTO_SAVE_SCANS] = enabled
        }
    }

    suspend fun updateOfflineMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[OFFLINE_MODE] = enabled
        }
    }

    suspend fun updateBiometricAuth(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[BIOMETRIC_AUTH] = enabled
        }
    }

    suspend fun updateAutoBackup(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[AUTO_BACKUP] = enabled
        }
    }

    suspend fun updateSelectedLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[SELECTED_LANGUAGE] = language
        }
    }

    suspend fun updateAppState(appState: AppState) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = appState.notificationsEnabled
            preferences[AUTO_SAVE_SCANS] = appState.autoSaveScans
            preferences[OFFLINE_MODE] = appState.offlineMode
            preferences[BIOMETRIC_AUTH] = appState.biometricAuth
            preferences[AUTO_BACKUP] = appState.autoBackup
            preferences[SELECTED_LANGUAGE] = appState.selectedLanguage
        }
    }

    suspend fun getCurrentAppState(): AppState {
        return appPreferencesFlow.first()
    }
}

// App preferences state
data class AppState(
    val notificationsEnabled: Boolean = true,
    val autoSaveScans: Boolean = true,
    val offlineMode: Boolean = false,
    val biometricAuth: Boolean = false,
    val autoBackup: Boolean = true,
    val selectedLanguage: String = "en"
)
