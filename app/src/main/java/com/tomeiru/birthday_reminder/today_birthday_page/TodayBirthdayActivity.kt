package com.tomeiru.birthday_reminder.today_birthday_page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tomeiru.birthday_reminder.today_birthday_page.ui.TodayBirthdayList
import com.tomeiru.birthday_reminder.today_birthday_page.ui.TodayBirthdayTopBar
import com.tomeiru.birthday_reminder.ui.theme.BirthdayReminderTheme

class TodayBirthdayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BirthdayReminderTheme {
                Scaffold(
                    topBar = {
                        TodayBirthdayTopBar(
                            onNavigationIconClick = { this.onBackPressedDispatcher.onBackPressed() }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(8.dp)
                            .padding(innerPadding),
                    ) {
                        TodayBirthdayList()
                    }
                }
            }
        }
    }
}