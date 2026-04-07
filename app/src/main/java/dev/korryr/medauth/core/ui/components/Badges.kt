package dev.korryr.medauth.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.korryr.medauth.core.designsystem.theme.DangerRed
import dev.korryr.medauth.core.designsystem.theme.SuccessGreen
import dev.korryr.medauth.core.designsystem.theme.UnknownGray
import dev.korryr.medauth.core.designsystem.theme.WarningAmber

enum class ResultStatus {
    VERIFIED,
    SUSPICIOUS,
    INVALID,
    UNKNOWN
}

@Composable
fun ResultStatusBadge(
    status: ResultStatus,
    modifier: Modifier = Modifier
) {
    val (backgroundColor, textColor, label) = when (status) {
        ResultStatus.VERIFIED -> Triple(SuccessGreen.copy(alpha = 0.15f), SuccessGreen, "Verified Authentic")
        ResultStatus.SUSPICIOUS -> Triple(WarningAmber.copy(alpha = 0.15f), WarningAmber, "Suspicious")
        ResultStatus.INVALID -> Triple(DangerRed.copy(alpha = 0.15f), DangerRed, "Potentially Counterfeit")
        ResultStatus.UNKNOWN -> Triple(UnknownGray.copy(alpha = 0.15f), UnknownGray, "Unknown Source")
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = label.uppercase(),
            color = textColor,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
