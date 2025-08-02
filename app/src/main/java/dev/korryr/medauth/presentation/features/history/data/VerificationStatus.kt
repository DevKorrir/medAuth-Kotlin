package dev.korryr.medauth.presentation.features.history.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class VerificationStatus(
    val displayName: String,
    val color: Color,
    val icon: ImageVector
) {
    VERIFIED("Verified ✓", Color(0xFF4CAF50), Icons.Default.CheckCircle),
    SUSPICIOUS("Suspicious ⚠", Color(0xFFFF9800), Icons.Default.Warning),
    FAKE("Fake ✗", Color(0xFFF44336), Icons.Default.Cancel),
    PENDING("Verifying...", Color(0xFF2196F3), Icons.Default.HourglassEmpty)
}