package com.tomeiru.birthday_reminder.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tomeiru.birthday_reminder.preferences.INFORMATIVE_NOTIFICATION_ENABLED
import com.tomeiru.birthday_reminder.preferences.INFORMATIVE_NOTIFICATION_HOURS
import com.tomeiru.birthday_reminder.preferences.INFORMATIVE_NOTIFICATION_MINUTES
import com.tomeiru.birthday_reminder.preferences.REMINDER_NOTIFICATION_ENABLED
import com.tomeiru.birthday_reminder.preferences.REMINDER_NOTIFICATION_HOURS
import com.tomeiru.birthday_reminder.preferences.REMINDER_NOTIFICATION_MINUTES
import com.tomeiru.birthday_reminder.preferences.dataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.Clock
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

@AndroidEntryPoint
class SetNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmManager: AlarmManager;

    private fun getNotificationDateTime(notificationTime: LocalTime): LocalDateTime {
        val now: LocalDateTime = LocalDateTime.now(Clock.systemDefaultZone())
        val notificationDate = if (now.toLocalTime() > notificationTime) now.toLocalDate()
            .plusDays(1) else now.toLocalDate()
        return LocalDateTime.of(notificationDate, notificationTime)
    }

    private fun setDailyInexactNotification(context: Context, intent: Intent, time: LocalTime) {
        val triggerMillis =
            getNotificationDateTime(time).atZone(ZoneId.systemDefault()).toEpochSecond() * 1000
        val intervalMillis: Long = 24 * 3600 * 1000
        val broadcast = PendingIntent.getBroadcast(
            context,
            intent.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerMillis,
            intervalMillis,
            broadcast
        )
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null || (intent.action != null && intent.action != Intent.ACTION_BOOT_COMPLETED)) return
        val preferences = runBlocking { context.dataStore.data.first() }
        if (preferences[INFORMATIVE_NOTIFICATION_ENABLED] == true) {
            setDailyInexactNotification(
                context,
                Intent(context, InformativeNotificationsReceiver::class.java),
                LocalTime.of(
                    preferences[INFORMATIVE_NOTIFICATION_HOURS] ?: 8,
                    preferences[INFORMATIVE_NOTIFICATION_MINUTES] ?: 0
                )
            )
        }
        if (preferences[REMINDER_NOTIFICATION_ENABLED] == true) {
            setDailyInexactNotification(
                context,
                Intent(context, ReminderNotificationsReceiver::class.java),
                LocalTime.of(
                    preferences[REMINDER_NOTIFICATION_HOURS] ?: 20,
                    preferences[REMINDER_NOTIFICATION_MINUTES] ?: 0
                )
            )
        }
    }
}