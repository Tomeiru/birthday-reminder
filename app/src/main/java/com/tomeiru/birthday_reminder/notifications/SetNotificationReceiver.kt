package com.tomeiru.birthday_reminder.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
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
        setDailyInexactNotification(
            context,
            Intent(context, InformativeNotificationsReceiver::class.java),
            LocalTime.of(8, 0)
        )
        setDailyInexactNotification(
            context,
            Intent(context, ReminderNotificationsReceiver::class.java),
            LocalTime.of(20, 0)
        )
    }
}