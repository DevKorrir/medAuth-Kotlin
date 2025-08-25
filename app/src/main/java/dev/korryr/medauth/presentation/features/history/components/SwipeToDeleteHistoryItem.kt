package dev.korryr.medauth.presentation.features.history.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import dev.korryr.medauth.presentation.features.history.data.MedicineHistory
import dev.korryr.medauth.presentation.features.history.data.VerificationStatus
import kotlin.math.roundToInt

@Composable
fun SwipeToDeleteHistoryItem(
    historyItem: MedicineHistory,
    onDelete: () -> Unit,
    onReport: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "swipe_offset"
    )
    
    val swipeThreshold = with(density) { 80.dp.toPx() }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Background actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Report action (left side)
            if (historyItem.verificationStatus == VerificationStatus.SUSPICIOUS || 
                historyItem.verificationStatus == VerificationStatus.FAKE) {
                IconButton(
                    onClick = onReport,
                    modifier = Modifier
                        .background(
                            Color(0xFFFF9800).copy(alpha = 0.2f),
                            CircleShape
                        )
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Report,
                        contentDescription = "Report",
                        tint = Color(0xFFFF9800),
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
            
            // Delete action (right side)
            IconButton(
                onClick = onDelete,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
                        CircleShape
                    )
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        
        // Main card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            when {
                                offsetX > swipeThreshold -> {
                                    // Swiped right - report (if applicable)
                                    if (historyItem.verificationStatus == VerificationStatus.SUSPICIOUS ||
                                        historyItem.verificationStatus == VerificationStatus.FAKE
                                    ) {
                                        onReport()
                                    }
                                    offsetX = 0f
                                }
                                offsetX < -swipeThreshold -> {
                                    // Swiped left - delete
                                    onDelete()
                                }
                                else -> {
                                    offsetX = 0f
                                }
                            }
                        }
                    ) { _, dragAmount ->
                        offsetX = (offsetX + dragAmount.x).coerceIn(-200f, 200f)
                    }
                }
                .clickable { onClick() },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            HistoryItemContent(historyItem = historyItem)
        }
    }
}