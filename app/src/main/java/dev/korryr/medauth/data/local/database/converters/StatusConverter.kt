package dev.korryr.medauth.data.local.database.converters

import androidx.room.TypeConverter
import dev.korryr.medauth.core.ui.components.ResultStatus

class StatusConverter {
    @TypeConverter
    fun fromStatus(status: ResultStatus): String {
        return status.name
    }

    @TypeConverter
    fun toStatus(value: String): ResultStatus {
        return try {
            ResultStatus.valueOf(value)
        } catch (e: Exception) {
            ResultStatus.UNKNOWN
        }
    }
}
