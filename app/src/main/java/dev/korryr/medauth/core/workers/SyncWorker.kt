package dev.korryr.medauth.core.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.korryr.medauth.data.local.database.ScanDao

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val scanDao: ScanDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.d("SyncWorker", "Starting offline sync...")
            val unsyncedScans = scanDao.getUnsyncedScans()

            if (unsyncedScans.isNotEmpty()) {
                // Here we would sync with a backend via a Retrofit network call
                // Simulating network upload:
                // networkApi.syncScans(unsyncedScans)
                
                val syncedIds = unsyncedScans.map { it.id }
                scanDao.markAsSynced(syncedIds)
                Log.d("SyncWorker", "Successfully synced ${unsyncedScans.size} items.")
            }
            
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Error during sync", e)
            Result.retry()
        }
    }
}
