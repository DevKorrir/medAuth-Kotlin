package dev.korryr.medauth.presentation.features.auth.proflile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable

@Composable
fun SupportSection() {
    SettingSectionCard(
        title = "Support",
        icon = Icons.Default.Help
    ) {
        SettingItem(
            title = "Help Center",
            subtitle = "Get help and find answers",
            icon = Icons.Default.HelpOutline
        )
        
        SettingItem(
            title = "Contact Us",
            subtitle = "Get in touch with our support team",
            icon = Icons.Default.ContactSupport
        )
        
        SettingItem(
            title = "Rate App",
            subtitle = "Share your feedback with us",
            icon = Icons.Default.Star
        )
        
        SettingItem(
            title = "About",
            subtitle = "App version and legal information",
            icon = Icons.Default.Info
        )
    }
}