package com.tomeiru.birthday_reminder

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.tomeiru.birthday_reminder.data.ApplicationContainer
import com.tomeiru.birthday_reminder.data.ApplicationDataContainer
import com.tomeiru.birthday_reminder.preferences.DISMISSED_POPUP_THIS_YEAR
import com.tomeiru.birthday_reminder.preferences.INFORMATIVE_NOTIFICATION_ENABLED
import com.tomeiru.birthday_reminder.preferences.INFORMATIVE_NOTIFICATION_HOURS
import com.tomeiru.birthday_reminder.preferences.INFORMATIVE_NOTIFICATION_MINUTES
import com.tomeiru.birthday_reminder.preferences.LAST_LAUNCHED_YEAR
import com.tomeiru.birthday_reminder.preferences.REMINDER_NOTIFICATION_ENABLED
import com.tomeiru.birthday_reminder.preferences.REMINDER_NOTIFICATION_HOURS
import com.tomeiru.birthday_reminder.preferences.REMINDER_NOTIFICATION_MINUTES
import com.tomeiru.birthday_reminder.preferences.dataStore
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

suspend fun runYearlyPopupRoutine(context: Context, today: LocalDate, lastLaunchedYear: Int?) {
    if (lastLaunchedYear != null && lastLaunchedYear == today.year) return
    context.dataStore.edit { preferences ->
        preferences[LAST_LAUNCHED_YEAR] = today.year
        preferences[DISMISSED_POPUP_THIS_YEAR] = lastLaunchedYear == null
    }
}

suspend fun runInformativeNotificationsRoutine(
    context: Context,
    preferences: Preferences
) {
    if (preferences[INFORMATIVE_NOTIFICATION_ENABLED] != null &&
        preferences[INFORMATIVE_NOTIFICATION_MINUTES] != null &&
        preferences[INFORMATIVE_NOTIFICATION_HOURS] != null
    )
        return
    context.dataStore.edit { prefs ->
        prefs[INFORMATIVE_NOTIFICATION_ENABLED] = true
        prefs[INFORMATIVE_NOTIFICATION_HOURS] = 8
        prefs[INFORMATIVE_NOTIFICATION_MINUTES] = 0
    }
}

suspend fun runReminderNotificationsRoutine(
    context: Context,
    preferences: Preferences
) {
    if (preferences[REMINDER_NOTIFICATION_ENABLED] != null &&
        preferences[REMINDER_NOTIFICATION_HOURS] != null &&
        preferences[REMINDER_NOTIFICATION_MINUTES] != null
    )
        return
    context.dataStore.edit { prefs ->
        prefs[REMINDER_NOTIFICATION_ENABLED] = true
        prefs[REMINDER_NOTIFICATION_HOURS] = 20
        prefs[REMINDER_NOTIFICATION_MINUTES] = 0
    }
}

suspend fun onLaunchPreferencesRoutine(today: LocalDate, context: Context) {
    val preferences = runBlocking { context.dataStore.data.first() }

    runYearlyPopupRoutine(context, today, preferences[LAST_LAUNCHED_YEAR])
    runInformativeNotificationsRoutine(context, preferences)
    runReminderNotificationsRoutine(context, preferences)
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