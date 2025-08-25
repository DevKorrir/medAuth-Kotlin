package dev.korryr.medauth.di

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.korryr.medauth.data.local.preferences.AppPreferences
import dev.korryr.medauth.data.local.preferences.themePreference.ThemePreferences
import dev.korryr.medauth.data.local.preferences.themePreference.thememanager.ThemeManager
import javax.inject.Singleton

@dagger.Module
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent::class)
object PreferencesModule {

    @dagger.Provides
    @Singleton
    fun provideThemePreferences(
        @ApplicationContext context: Context
    ): ThemePreferences = ThemePreferences(context)

    @dagger.Provides
    @Singleton
    fun provideAppPreferences(
        @ApplicationContext context: Context
    ): AppPreferences = AppPreferences(context)

    @dagger.Provides
    @Singleton
    fun provideThemeManager(
        themePreferences: ThemePreferences
    ): ThemeManager = ThemeManager(themePreferences)
}