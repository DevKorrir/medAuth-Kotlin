package dev.korryr.medauth.presentation.features.auth.proflile.components

import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.runtime.Composable
import dev.korryr.medauth.data.local.preferences.themePreference.data.state.ThemeState

// Appearance settings section
@Composable
fun AppearanceSection(
    themeState: ThemeState,
    onToggleDarkTheme: () -> Unit,
    onToggleAutoTheme: () -> Unit,
    onToggleDynamicColor: () -> Unit
) {
    SettingSectionCard(
        title = "Appearance",
        icon = Icons.Default.Palette
    ) {
        SettingItem(
            title = "Follow System Theme",
            subtitle = if (themeState.isAutoTheme) 
                "App theme follows system setting" 
            else "Manual theme selection enabled",
            icon = Icons.Default.PhoneAndroid,
            hasSwitch = true,
            switchState = themeState.isAutoTheme,
            onClick = onToggleAutoTheme
        )
        
        // Only show manual theme toggle when not following system
        AnimatedVisibility(
            visible = !themeState.isAutoTheme,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            SettingItem(
                title = "Dark Mode",
                subtitle = if (themeState.isDarkTheme) 
                    "Dark theme is active" 
                else "Light theme is active",
                icon = if (themeState.isDarkTheme) 
                    Icons.Default.DarkMode 
                else Icons.Default.LightMode,
                hasSwitch = true,
                switchState = themeState.isDarkTheme,
                onClick = onToggleDarkTheme
            )
        }
        
        // Only show dynamic colors on Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            SettingItem(
                title = "Dynamic Colors",
                subtitle = if (themeState.isDynamicColor) 
                    "Colors adapt to your wallpaper" 
                else "Using app default colors",
                icon = Icons.Default.ColorLens,
                hasSwitch = true,
                switchState = themeState.isDynamicColor,
                onClick = onToggleDynamicColor
            )
        }
    }
}