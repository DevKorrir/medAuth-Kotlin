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
import dev.korryr.medauth.data.local.preferences.ThemePreferences
import dev.korryr.medauth.data.local.preferences.ThemeState
import dev.korryr.medauth.presentation.features.auth.proflile.components.AccountSection
import dev.korryr.medauth.presentation.features.auth.proflile.components.ProfileHeader
import dev.korryr.medauth.presentation.features.auth.proflile.components.SettingsSection
import dev.korryr.medauth.presentation.features.auth.proflile.components.StatsCards
import dev.korryr.medauth.presentation.features.auth.proflile.components.SupportSection
import dev.korryr.medauth.presentation.features.auth.proflile.data.UserProfile
import androidx.compose.runtime.rememberCoroutineScope

import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    themeState: ThemeState,
    themePreferences: ThemePreferences,
    modifier: Modifier = Modifier
) {
    val userProfile = remember { UserProfile() }
    val scope = rememberCoroutineScope()

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
                themeState = themeState,
                onThemeToggle = { isDark : Boolean ->
                    scope.launch {
                        themePreferences.saveThemePreference(isDark)
                    }
                },
                onAutoThemeToggle = { isAuto ->
                    scope.launch {
                        themePreferences.saveAutoThemePreference(isAuto)
                    }
                },
                onDynamicColorToggle = { isDynamic ->
                    scope.launch {
                        themePreferences.saveDynamicColorPreference(isDynamic)
                    }
                }
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