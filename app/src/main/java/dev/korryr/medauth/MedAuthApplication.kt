package dev.korryr.medauth


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MedAuthApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any app-wide components here if needed
        // For example: logging, crash reporting, etc.
    }
}