package com.tomeiru.birthday_reminder.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.tomeiru.birthday_reminder.R
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.data.database.birthday.getAge
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class ReminderNotificationsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: BirthdayRepository;

    @Inject
    lateinit var notificationManager: NotificationManager;

    private fun createNotification(
        context: Context,
        today: LocalDate,
        birthdays: List<Birthday>
    ): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, "reminder_notifications")
            .setContentIntent(getOnClickPendingIntent(context))
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("There is still time!")
        if (birthdays.size != 1) {
            return notificationBuilder.setContentTitle("Don't forget! There are still ${birthdays.size} people you didn't wish the birthday today!")
                .build()
        }
        val birthday = birthdays[0]
        val age = birthday.getAge(today)
        val ageText = if (age == null) "" else " They're $age years old today!"
        return notificationBuilder.setContentTitle("Don't forget ${birthday.name}'s birthday today!${ageText}")
            .build()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        val today: LocalDate = LocalDate.now(Clock.systemDefaultZone())
        val birthdays: List<Birthday>
        runBlocking {
            birthdays =
                repository.getUncelebratedBirthdaysByDate(today.dayOfMonth, today.monthValue)
                    .first()
        }
        if (birthdays.isEmpty()) return
        val notification = createNotification(context, today, birthdays)
        notificationManager.notify(2, notification)
    }
}