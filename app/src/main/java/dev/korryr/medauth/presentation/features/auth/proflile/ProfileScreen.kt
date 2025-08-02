package dev.korryr.medauth.presentation.features.auth.proflile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.korryr.medauth.data.local.preferences.AppState
import dev.korryr.medauth.data.local.preferences.themePreference.ThemePreferences
import dev.korryr.medauth.data.local.preferences.themePreference.data.state.ThemeState
import dev.korryr.medauth.data.local.preferences.themePreference.viewModel.ErrorSnackbar
import dev.korryr.medauth.data.local.preferences.themePreference.viewModel.ThemeViewModel
import dev.korryr.medauth.data.local.preferences.themePreference.viewModel.collectErrorAsState
import dev.korryr.medauth.presentation.features.auth.proflile.components.AccountSection
import dev.korryr.medauth.presentation.features.auth.proflile.components.AppearanceSection
import dev.korryr.medauth.presentation.features.auth.proflile.components.ProfileHeader
import dev.korryr.medauth.presentation.features.auth.proflile.components.SettingItem
import dev.korryr.medauth.presentation.features.auth.proflile.components.SettingSectionCard
import dev.korryr.medauth.presentation.features.auth.proflile.components.SettingsSection
import dev.korryr.medauth.presentation.features.auth.proflile.components.StatsCards
import dev.korryr.medauth.presentation.features.auth.proflile.components.SupportSection
import dev.korryr.medauth.presentation.features.auth.proflile.data.UserProfile
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    themeViewModel: ThemeViewModel = hiltViewModel(),
    themePreferences: ThemePreferences,
    themeState: ThemeState,
    appState: AppState,
    modifier: Modifier = Modifier
) {
    val userProfile = remember { UserProfile() }
    val scope = rememberCoroutineScope()

    val errorState by themeViewModel.collectErrorAsState()

    // Handle errors with Snackbar
    ErrorSnackbar(
        errorMessage = errorState,
        onDismiss = { themeViewModel.clearError() }
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProfileHeader(userProfile = userProfile)
        }

        item {
            StatsCards(userProfile = userProfile)
        }

        item {
            AppearanceSection(
                themeState = themeState,
                onToggleDarkTheme = { themeViewModel.toggleDarkTheme() },
                onToggleAutoTheme = { themeViewModel.toggleAutoTheme() },
                onToggleDynamicColor = { themeViewModel.toggleDynamicColor() }
            )
        }

        item {
            PreferencesSection(
                appState = appState,
                onToggleNotifications = { themeViewModel.toggleNotifications() },
                onToggleAutoSave = { themeViewModel.toggleAutoSaveScans() },
                onToggleOfflineMode = { themeViewModel.toggleOfflineMode() },
                onLanguageChange = { language -> themeViewModel.updateLanguage(language) }
            )
        }

        item {
            AccountSection()
        }

        item {
            SupportSection()
        }

    }
}


@Composable
private fun PreferencesSection(
    appState: AppState,
    onToggleNotifications: () -> Unit,
    onToggleAutoSave: () -> Unit,
    onToggleOfflineMode: () -> Unit,
    onLanguageChange: (String) -> Unit
) {
    SettingSectionCard(
        title = "App Preferences",
        icon = Icons.Default.Settings
    ) {
        SettingItem(
            title = "Push Notifications",
            subtitle = if (appState.notificationsEnabled)
                "Receiving verification alerts"
            else "Notifications are disabled",
            icon = if (appState.notificationsEnabled)
                Icons.Default.Notifications
            else Icons.Default.NotificationsOff,
            hasSwitch = true,
            switchState = appState.notificationsEnabled,
            onClick = onToggleNotifications
        )

        SettingItem(
            title = "Auto-Save Scans",
            subtitle = if (appState.autoSaveScans)
                "Scan results saved automatically"
            else "Manual save required",
            icon = if (appState.autoSaveScans)
                Icons.Default.Save
            else Icons.Default.SaveAs,
            hasSwitch = true,
            switchState = appState.autoSaveScans,
            onClick = onToggleAutoSave
        )

        SettingItem(
            title = "Offline Mode",
            subtitle = if (appState.offlineMode)
                "Works without internet connection"
            else "Requires internet for verification",
            icon = if (appState.offlineMode)
                Icons.Default.CloudOff
            else Icons.Default.CloudQueue,
            hasSwitch = true,
            switchState = appState.offlineMode,
            onClick = onToggleOfflineMode
        )

        SettingItem(
            title = "Language & Region",
            subtitle = getLanguageDisplayName(appState.selectedLanguage),
            icon = Icons.Default.Language,
            onClick = {
                // TODO: Show language picker dialog
                // For now, cycle through available languages
                val nextLanguage = when (appState.selectedLanguage) {
                    "en" -> "sw" // Swahili
                    "sw" -> "fr" // French
                    "fr" -> "ar" // Arabic
                    else -> "en" // English
                }
                onLanguageChange(nextLanguage)
            }
        )
    }
}

// Helper function for language display names
private fun getLanguageDisplayName(languageCode: String): String {
    return when (languageCode) {
        "en" -> "English (Kenya)"
        "sw" -> "Kiswahili (Kenya)"
        "fr" -> "Français (Kenya)"
        "ar" -> "العربية (Kenya)"
        else -> "English (Kenya)"
    }
}