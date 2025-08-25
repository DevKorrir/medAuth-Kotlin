package dev.korryr.medauth.presentation.features.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.korryr.medauth.presentation.features.history.data.MedicineHistory

@Composable
fun HistoryItemContent(
    historyItem: MedicineHistory
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Medicine icon/image
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = historyItem.verificationStatus.color.copy(alpha = 0.1f),
            modifier = Modifier.size(56.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (historyItem.imageUrl.isNotEmpty()) {
                    // AsyncImage for medicine photo if available
                    // AsyncImage(model = historyItem.imageUrl, ...)
                    Icon(
                        imageVector = Icons.Default.Medication,
                        contentDescription = "Medicine",
                        tint = historyItem.verificationStatus.color,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Medication,
                        contentDescription = "Medicine",
                        tint = historyItem.verificationStatus.color,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
        
        // Medicine details
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = historyItem.medicineName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = "Batch: ${historyItem.batchNumber}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                fontSize = 13.sp
            )
            
            Text(
                text = "${historyItem.manufacturer} • Exp: ${historyItem.expiryDate}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        
        // Status and time
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Status badge
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = historyItem.verificationStatus.color.copy(alpha = 0.1f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = historyItem.verificationStatus.icon,
                        contentDescription = historyItem.verificationStatus.displayName,
                        modifier = Modifier.size(12.dp),
                        tint = historyItem.verificationStatus.color
                    )
                    Text(
                        text = historyItem.verificationStatus.displayName.split(" ")[0],
                        style = MaterialTheme.typography.labelSmall,
                        color = historyItem.verificationStatus.color,
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp
                    )
                }
            }
            
            Text(
                text = historyItem.scanDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontSize = 11.sp
            )
            
            Text(
                text = historyItem.scanTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                fontSize = 10.sp
            )
        }
    }
}