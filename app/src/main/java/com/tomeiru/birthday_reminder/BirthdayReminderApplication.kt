package com.tomeiru.birthday_reminder

import DISMISSED_POPUP_THIS_YEAR
import LAST_LAUNCHED_YEAR
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.datastore.preferences.core.edit
import com.tomeiru.birthday_reminder.data.ApplicationContainer
import com.tomeiru.birthday_reminder.data.ApplicationDataContainer
import dagger.hilt.android.HiltAndroidApp
import dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

suspend fun onLaunchPreferencesRoutine(today: LocalDate, context: Context) {
    val lastLaunchedYear: Int? = runBlocking { context.dataStore.data.first()[LAST_LAUNCHED_YEAR] }

    if (lastLaunchedYear == null || lastLaunchedYear != today.year) {
        context.dataStore.edit { preferences ->
            preferences[LAST_LAUNCHED_YEAR] = today.year
            preferences[DISMISSED_POPUP_THIS_YEAR] = lastLaunchedYear == null
        }
    }
}

fun createNotificationChannels(context: Context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val informativeChannel = NotificationChannel(
        "informative_notifications",
        "Informative Notification",
        NotificationManager.IMPORTANCE_HIGH
    )
    informativeChannel.description =
        "Tells you how many birthdays there is today, usually in the morning"
    val reminderChannel = NotificationChannel(
        "reminder_notifications",
        "Reminder Notifications",
        NotificationManager.IMPORTANCE_HIGH
    )
    informativeChannel.description =
        "Tells you how many birthdays you didn't wish yet today, usually in the evening"
    notificationManager.createNotificationChannel(informativeChannel)
    notificationManager.createNotificationChannel(reminderChannel)
}

@HiltAndroidApp
class BirthdayReminderApplication : Application() {
    lateinit var container: ApplicationContainer;

    override fun onCreate() {
        super.onCreate()
        container = ApplicationDataContainer(this)
        createNotificationChannels(this)
        runBlocking {
            onLaunchPreferencesRoutine(container.today, this@BirthdayReminderApplication)
        }
    }
}