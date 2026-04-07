package dev.korryr.medauth.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanDao {
    @Query("SELECT * FROM scan_history ORDER BY timestamp DESC")
    fun getAllScans(): Flow<List<ScanEntity>>

    @Query("SELECT * FROM scan_history WHERE isSynced = 0")
    suspend fun getUnsyncedScans(): List<ScanEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScan(scan: ScanEntity)

    @Query("UPDATE scan_history SET isSynced = 1 WHERE id IN (:scanIds)")
    suspend fun markAsSynced(scanIds: List<Int>)

    @Query("DELETE FROM scan_history")
    suspend fun clearHistory()
}
