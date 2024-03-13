package com.tomeiru.birthday_reminder.notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.tomeiru.birthday_reminder.R
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.data.database.birthday.getAge
import com.tomeiru.birthday_reminder.today_birthday_page.TodayBirthdayActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

fun getOnClickPendingIntent(context: Context): PendingIntent {
    val intent = Intent(context, TodayBirthdayActivity::class.java)
    val bundle = Bundle()
    bundle.putInt("screen", 1)
    intent.putExtras(bundle)
    return PendingIntent.getActivity(
        context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}

@AndroidEntryPoint
class InformativeNotificationsReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: BirthdayRepository;

    @Inject
    lateinit var notificationManager: NotificationManager;

    private fun createNotification(
        context: Context,
        today: LocalDate,
        birthdays: List<Birthday>
    ): Notification {
        val notificationBuilder = NotificationCompat.Builder(context, "informative_notifications")
            .setContentIntent(getOnClickPendingIntent(context))
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("Don't forget to wish them!")
        if (birthdays.size != 1) {
            return notificationBuilder.setContentTitle("${birthdays.size} people you registered celebrate their birthday today!")
                .build()
        }
        val birthday = birthdays[0]
        val age = birthday.getAge(today)
        val ageText = if (age == null) "" else " They're $age years old today!"
        return notificationBuilder.setContentTitle("It's ${birthday.name}'s birthday today!${ageText}")
            .build()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return
        val today: LocalDate = LocalDate.now(Clock.systemDefaultZone())
        val birthdays: List<Birthday>
        runBlocking {
            birthdays = repository.getBirthdaysByDate(today.dayOfMonth, today.monthValue).first()
        }
        if (birthdays.isEmpty()) return
        val notification = createNotification(context, today, birthdays)
        notificationManager.notify(1, notification)
    }
}