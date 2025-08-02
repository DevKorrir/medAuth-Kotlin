package dev.korryr.medauth.presentation.features.auth.proflile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.korryr.medauth.presentation.features.auth.proflile.data.UserProfile

@Composable
fun StatsCards(userProfile: UserProfile) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            title = "Verifications",
            value = userProfile.verificationsCount.toString(),
            icon = Icons.Default.CheckCircle,
            modifier = Modifier.weight(1f),
            color = Color(0xFF4CAF50)
        )
        
        StatCard(
            title = "Scans",
            value = userProfile.scanCount.toString(),
            icon = Icons.Default.QrCodeScanner,
            modifier = Modifier.weight(1f),
            color = Color(0xFF2196F3)
        )
        
        StatCard(
            title = "Since",
            value = userProfile.joinDate,
            icon = Icons.Default.CalendarToday,
            modifier = Modifier.weight(1f),
            color = Color(0xFF9C27B0)
        )
    }
}