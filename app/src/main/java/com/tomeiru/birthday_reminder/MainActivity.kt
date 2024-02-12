package com.tomeiru.birthday_reminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.tomeiru.birthday_reminder.reset_celebrated.ResetCelebratedPopup
import com.tomeiru.birthday_reminder.ui.BirthdayReminder
import com.tomeiru.birthday_reminder.ui.theme.BirthdayReminderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BirthdayReminderTheme {
                BirthdayReminder()
                ResetCelebratedPopup()
            }
        }
    }
}