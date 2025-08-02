package dev.korryr.medauth.presentation.features.auth.proflile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable

@Composable
fun SettingsSection(
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    SettingSectionCard(
        title = "Preferences",
        icon = Icons.Default.Settings
    ) {
        SettingItem(
            title = "Dark Mode",
            subtitle = "Switch between light and dark themes",
            icon = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
            hasSwitch = true,
            switchState = isDarkTheme,
            onClick = { onThemeToggle(!isDarkTheme) }
        )
        
        SettingItem(
            title = "Notifications",
            subtitle = "Manage your notification preferences",
            icon = Icons.Default.Notifications
        )
        
        SettingItem(
            title = "Language",
            subtitle = "Choose your preferred language",
            icon = Icons.Default.Language
        )
        
        SettingItem(
            title = "Auto-Scan",
            subtitle = "Enable automatic medicine scanning",
            icon = Icons.Default.CameraAlt,
            hasSwitch = true,
            switchState = true
        )
    }
}