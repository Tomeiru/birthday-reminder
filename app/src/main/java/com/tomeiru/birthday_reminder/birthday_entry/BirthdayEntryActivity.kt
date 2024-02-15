package com.tomeiru.birthday_reminder.birthday_entry

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
import com.tomeiru.birthday_reminder.BirthdayFormStartingValues
import com.tomeiru.birthday_reminder.ViewModelProvider
import com.tomeiru.birthday_reminder.ui.theme.BirthdayReminderTheme

fun returnFormSelectedValue(number: Int): Int? {
    if (number == 0) return null
    return number
}

class BirthdayEntryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras ?: return
        val isEdit = bundle.getBoolean("edit", false)
        ViewModelProvider.setFormDefaultValues(
            BirthdayFormStartingValues(
                bundle.getString("name", ""),
                bundle.getString("day", ""),
                returnFormSelectedValue(bundle.getInt("month", 0)),
                returnFormSelectedValue(bundle.getInt("year", 0)),
                bundle.getBoolean("celebrated", false)
            )
        )
        setContent {
            BirthdayReminderTheme {
                Scaffold(
                    topBar = {
                        BirthdayEntryTopBar(
                            edit = isEdit,
                            onNavigationIconClick = { this.onBackPressedDispatcher.onBackPressed() })
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(8.dp)
                            .padding(innerPadding),
                    ) {
                        BirthdayEntryForm(
                            isEdit
                        )
                    }
                }
            }
        }
    }
}