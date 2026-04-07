package dev.korryr.medauth.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.korryr.medauth.data.local.database.converters.StatusConverter

@Database(entities = [ScanEntity::class], version = 1, exportSchema = false)
@TypeConverters(StatusConverter::class)
abstract class MedAuthDatabase : RoomDatabase() {
    abstract fun scanDao(): ScanDao
}
