package dev.korryr.medauth.presentation.features.auth.proflile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Security
import androidx.compose.runtime.Composable

@Composable
fun AccountSection() {
    SettingSectionCard(
        title = "Account",
        icon = Icons.Default.AccountCircle
    ) {
        SettingItem(
            title = "Edit Profile",
            subtitle = "Update your personal information",
            icon = Icons.Default.Edit
        )
        
        SettingItem(
            title = "Security",
            subtitle = "Password and security settings",
            icon = Icons.Default.Security
        )
        
        SettingItem(
            title = "Privacy",
            subtitle = "Control your data and privacy",
            icon = Icons.Default.PrivacyTip
        )
        
        SettingItem(
            title = "Backup & Sync",
            subtitle = "Manage your data backup",
            icon = Icons.Default.CloudSync
        )
    }
}