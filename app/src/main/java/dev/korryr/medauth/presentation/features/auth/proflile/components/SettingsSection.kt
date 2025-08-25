package dev.korryr.medauth.presentation.features.auth.proflile.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.korryr.medauth.data.local.preferences.themePreference.data.state.ThemeState

@Composable
fun SettingsSection(
    themeState: ThemeState,
    onThemeToggle: (Boolean) -> Unit,
    onAutoThemeToggle: (Boolean) -> Unit,
    onDynamicColorToggle: (Boolean) -> Unit
) {

    SettingSectionCard(
        title = "Appearance",
        icon = Icons.Default.Palette
    ) {
        SettingItem(
            title = "Follow System Theme",
            subtitle = if (themeState.isAutoTheme) "App theme follows system setting" else "Manual theme selection",
            icon = Icons.Default.PhoneAndroid,
            hasSwitch = true,
            switchState = themeState.isAutoTheme,
            onClick = { onAutoThemeToggle(!themeState.isAutoTheme) }
        )

        // Only show manual theme toggle when not following system
        if (!themeState.isAutoTheme) {
            SettingItem(
                title = "Dark Mode",
                subtitle = if (themeState.isDarkTheme) "Dark theme is enabled" else "Light theme is enabled",
                icon = if (themeState.isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                hasSwitch = true,
                switchState = themeState.isDarkTheme,
                onClick = { onThemeToggle(!themeState.isDarkTheme) }
            )
        }

        SettingItem(
            title = "Dynamic Colors",
            subtitle = if (themeState.isDynamicColor) "Colors adapt to your wallpaper" else "Use app default colors",
            icon = Icons.Default.ColorLens,
            hasSwitch = true,
            switchState = themeState.isDynamicColor,
            onClick = { onDynamicColorToggle(!themeState.isDynamicColor) }
        )
    }

    Spacer(modifier = Modifier.height(8.dp))


    SettingSectionCard(
        title = "Preferences",
        icon = Icons.Default.Settings
    ) {
        SettingItem(
            title = "Push Notifications",
            subtitle = "Get alerts for medicine verification results",
            icon = Icons.Default.Notifications,
            hasSwitch = true,
            switchState = true
        )

        SettingItem(
            title = "Language & Region",
            subtitle = "English (Kenya)",
            icon = Icons.Default.Language
        )

        SettingItem(
            title = "Auto-Save Scans",
            subtitle = "Automatically save all scan results",
            icon = Icons.Default.Save,
            hasSwitch = true,
            switchState = true
        )

        SettingItem(
            title = "Offline Mode",
            subtitle = "Enable offline medicine verification",
            icon = Icons.Default.CloudOff,
            hasSwitch = true,
            switchState = false
        )
    }
}