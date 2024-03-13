package com.tomeiru.birthday_reminder.dependency_injection

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.OfflineBirthdayRepository
import com.tomeiru.birthday_reminder.data.database.BirthdayDatabase
import com.tomeiru.birthday_reminder.preferences.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOfflineBirthdayRepository(app: Application): BirthdayRepository {
        return OfflineBirthdayRepository(BirthdayDatabase.getInstance(app).birthdayDao())
    }

    @Provides
    @Singleton
    fun provideNotificationManager(app: Application): NotificationManager {
        return app.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun provideAlarmManager(app: Application): AlarmManager {
        return app.getSystemService(AlarmManager::class.java)
    }

    @Provides
    @Singleton
    fun provideDataStore(app: Application): DataStore<Preferences> {
        return app.dataStore
    }
}