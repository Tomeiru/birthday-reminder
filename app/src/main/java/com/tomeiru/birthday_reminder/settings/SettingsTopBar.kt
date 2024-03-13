package com.tomeiru.birthday_reminder.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar() {
    LargeTopAppBar(
        title = {
            Text(
                text = "Settings",
                fontSize = 32.sp,
            )
        },
    )
}