package dev.korryr.medauth.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.korryr.medauth.core.ui.components.ResultStatus

@Entity(tableName = "scan_history")
data class ScanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val barcode: String,
    val drugName: String?,
    val manufacturer: String?,
    val timestamp: Long,
    val verificationStatus: ResultStatus,
    val isSynced: Boolean = false
)
