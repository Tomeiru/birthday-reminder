package com.tomeiru.birthday_reminder.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.birthday_catalog.ConfirmationDialog
import com.tomeiru.birthday_reminder.setUpNotifications
import com.tomeiru.birthday_reminder.settings.celebration.CelebrationSection
import com.tomeiru.birthday_reminder.settings.notifications.NotificationSection
import com.tomeiru.birthday_reminder.settings.notifications.TimePickerDialog
import kotlinx.coroutines.runBlocking
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(
    viewModel: SettingsViewModel = viewModel()
) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        NotificationSection()
        CelebrationSection()
        if (viewModel.keysUsingTimePickerState.value != null) {
            TimePickerDialog(
                onCancel = { viewModel.keysUsingTimePickerState.value = null },
                onConfirm = {
                    runBlocking {
                        viewModel.setAlarmTime(
                            viewModel.keysUsingTimePickerState.value!!.hours,
                            viewModel.keysUsingTimePickerState.value!!.minutes,
                            LocalTime.of(
                                viewModel.timePickerState.value.hour,
                                viewModel.timePickerState.value.minute
                            )
                        )
                    }
                    setUpNotifications(context)
                    viewModel.keysUsingTimePickerState.value = null

                }) {
                TimePicker(viewModel.timePickerState.value)
            }
        }
        ConfirmationDialog(
            title = "Reset Celebrations?",
            showPopup = viewModel.resetCelebrationConfirmationDialog.value,
            content = {
                Text(
                    text = "Are you sure?",
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                )
                Text(
                    text = "Once this action has been launched, it is not cancellable nor undoable",
                    fontSize = 12.sp,
                    lineHeight = 20.sp,
                )
            },
            action = {
                runBlocking {
                    viewModel.resetAllCelebratedBirthdays()
                }
            },
            onPopupDismiss = {
                viewModel.resetCelebrationConfirmationDialog.value = false
            }
        )
    }
}