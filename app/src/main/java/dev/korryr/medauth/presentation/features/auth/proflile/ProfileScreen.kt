package dev.korryr.medauth.presentation.features.auth.proflile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.korryr.medauth.presentation.features.auth.proflile.components.AccountSection
import dev.korryr.medauth.presentation.features.auth.proflile.components.ProfileHeader
import dev.korryr.medauth.presentation.features.auth.proflile.components.SettingsSection
import dev.korryr.medauth.presentation.features.auth.proflile.components.StatsCards
import dev.korryr.medauth.presentation.features.auth.proflile.components.SupportSection
import dev.korryr.medauth.presentation.features.auth.proflile.data.UserProfile

@Composable
fun ProfileScreen(
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfile = remember { UserProfile() }

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
            SettingsSection(
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
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