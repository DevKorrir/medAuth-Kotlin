package dev.korryr.medauth.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.korryr.medauth.data.local.database.MedAuthDatabase
import dev.korryr.medauth.data.local.database.ScanDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMedAuthDatabase(@ApplicationContext context: Context): MedAuthDatabase {
        return Room.databaseBuilder(
            context,
            MedAuthDatabase::class.java,
            "medauth_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideScanDao(database: MedAuthDatabase): ScanDao = database.scanDao()
}
