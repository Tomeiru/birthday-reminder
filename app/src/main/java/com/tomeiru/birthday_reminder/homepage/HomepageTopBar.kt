package com.tomeiru.birthday_reminder.homepage

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageTopBar() {
    LargeTopAppBar(
        title = {
            Text(
                text = "Birthday Reminder",
                fontSize = 32.sp,
            )
        },
    )
}