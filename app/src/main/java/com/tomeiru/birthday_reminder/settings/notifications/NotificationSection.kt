package com.tomeiru.birthday_reminder.settings.notifications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowRight
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.settings.SectionTitle
import com.tomeiru.birthday_reminder.settings.SettingsViewModel
import kotlinx.coroutines.runBlocking
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSetting(
    title: String,
    preferencesKeys: SettingsViewModel.NotificationPreferenceKeys,
    viewModel: SettingsViewModel = viewModel()
) {
    val preferences = viewModel.preferences.collectAsState()
    if (preferences.value == null) return
    val enabled = preferences.value!![preferencesKeys.enabled] == true
    val time = LocalTime.of(
        preferences.value!![preferencesKeys.hours] ?: 0,
        preferences.value!![preferencesKeys.minutes] ?: 0
    )
    Card(
        onClick = {
            viewModel.timePickerState.value = TimePickerState(
                time.hour,
                time.minute,
                false,
            )
            viewModel.keysUsingTimePickerState.value = preferencesKeys
        },
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = enabled,
                    onCheckedChange = {
                        runBlocking {
                            viewModel.setAlarmEnableStatus(
                                preferencesKeys.enabled,
                                it
                            )
                        }
                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = time.format(DateTimeFormatter.ofPattern("hh:mm a")))
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowRight,
                    contentDescription = null
                )
            }
        }

    }
}

@Composable
fun NotificationSection(
    viewModel: SettingsViewModel = viewModel()
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SectionTitle(
            Icons.Outlined.Notifications,
            "Notifications"
        )
        NotificationSetting("Daily Informative Notification", viewModel.keys[0])
        NotificationSetting("Daily Reminder Notification", viewModel.keys[1])
    }
}