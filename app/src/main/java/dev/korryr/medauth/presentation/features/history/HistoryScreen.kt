package dev.korryr.medauth.presentation.features.history

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.korryr.medauth.presentation.features.history.components.EmptyHistoryState
import dev.korryr.medauth.presentation.features.history.components.HistoryHeader
import dev.korryr.medauth.presentation.features.history.components.SwipeToDeleteHistoryItem
import dev.korryr.medauth.presentation.features.history.data.MedicineHistory
import dev.korryr.medauth.presentation.features.history.data.VerificationStatus

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    onReportDrug: (MedicineHistory) -> Unit = {},
    onDeleteHistory: (MedicineHistory) -> Unit = {},
    onItemClick: (MedicineHistory) -> Unit = {}
) {
    // Sample data - replace with your actual data source
    var historyList by remember {
        mutableStateOf(
            listOf(
                MedicineHistory(
                    id = "1",
                    medicineName = "Paracetamol 500mg",
                    batchNumber = "PAR2024001",
                    scanDate = "Today",
                    scanTime = "2:30 PM",
                    verificationStatus = VerificationStatus.VERIFIED,
                    manufacturer = "Cosmos Pharmaceuticals",
                    expiryDate = "Dec 2025"
                ),
                MedicineHistory(
                    id = "2",
                    medicineName = "Amoxicillin 250mg",
                    batchNumber = "AMX2024002",
                    scanDate = "Yesterday",
                    scanTime = "10:15 AM",
                    verificationStatus = VerificationStatus.SUSPICIOUS,
                    manufacturer = "Beta Healthcare",
                    expiryDate = "Mar 2025"
                ),
                MedicineHistory(
                    id = "3",
                    medicineName = "Ibuprofen 400mg",
                    batchNumber = "IBU2024003",
                    scanDate = "2 days ago",
                    scanTime = "4:45 PM",
                    verificationStatus = VerificationStatus.FAKE,
                    manufacturer = "Unknown Manufacturer",
                    expiryDate = "Expired"
                ),
                MedicineHistory(
                    id = "4",
                    medicineName = "Aspirin 75mg",
                    batchNumber = "ASP2024004",
                    scanDate = "3 days ago",
                    scanTime = "11:20 AM",
                    verificationStatus = VerificationStatus.VERIFIED,
                    manufacturer = "PharmaCorp Ltd",
                    expiryDate = "Sep 2026"
                ),
                MedicineHistory(
                    id = "5",
                    medicineName = "Metformin 500mg",
                    batchNumber = "MET2024005",
                    scanDate = "1 week ago",
                    scanTime = "1:10 PM",
                    verificationStatus = VerificationStatus.PENDING,
                    manufacturer = "Global Pharma",
                    expiryDate = "Nov 2025"
                )
            )
        )
    }

    var showClearAllDialog by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with stats and actions
            HistoryHeader(
                totalScans = historyList.size,
                verifiedCount = historyList.count { it.verificationStatus == VerificationStatus.VERIFIED },
                suspiciousCount = historyList.count { it.verificationStatus == VerificationStatus.SUSPICIOUS },
                fakeCount = historyList.count { it.verificationStatus == VerificationStatus.FAKE },
                onClearAll = {
                    if (historyList.isNotEmpty()) showClearAllDialog = true
                }
            )

            if (historyList.isEmpty()) {
                // Empty state
                EmptyHistoryState(
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // History list
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                       items = historyList,
                        key = { it.id }
                    ) { historyItem ->
                        SwipeToDeleteHistoryItem(
                            historyItem = historyItem,
                            onDelete = {
                                historyList = historyList.filter { it.id != historyItem.id }
                                onDeleteHistory(historyItem)
                            },
                            onReport = { onReportDrug(historyItem) },
                            onClick = { onItemClick(historyItem) },
                           // modifier = Modifier.animateItemPlacement()
                        )
                    }

                }
            }
        }

        // Clear all confirmation dialog
        if (showClearAllDialog) {
            AlertDialog(
                onDismissRequest = { showClearAllDialog = false },
                title = {
                    Text(
                        text = "Clear All History?",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "This will permanently delete all your scan history. This action cannot be undone.",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            historyList = emptyList()
                            showClearAllDialog = false
                        }
                    ) {
                        Text(
                            text = "Clear All",
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showClearAllDialog = false }
                    ) {
                        Text("Cancel")
                    }
                },
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}