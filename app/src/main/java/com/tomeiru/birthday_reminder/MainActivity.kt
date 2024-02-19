package com.tomeiru.birthday_reminder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tomeiru.birthday_reminder.notifications.SetNotificationReceiver
import com.tomeiru.birthday_reminder.reset_celebrated.ResetCelebratedPopup
import com.tomeiru.birthday_reminder.ui.BirthdayReminder
import com.tomeiru.birthday_reminder.ui.theme.BirthdayReminderTheme

fun setUpNotifications(context: Context) {
    val notificationSetter = Intent(context, SetNotificationReceiver::class.java)
    context.sendBroadcast(notificationSetter)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val defaultScreen = bundle?.getInt("screen", 0) ?: 0
        setUpNotifications(this.applicationContext)
        setContent {
            BirthdayReminderTheme {
                BirthdayReminder(defaultScreen)
                ResetCelebratedPopup()
            }
        }
    }
}