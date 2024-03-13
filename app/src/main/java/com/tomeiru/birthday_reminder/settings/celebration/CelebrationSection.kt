package com.tomeiru.birthday_reminder.settings.celebration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.settings.SectionTitle
import com.tomeiru.birthday_reminder.settings.SettingsViewModel

@Composable
fun CelebrationSection(
    viewModel: SettingsViewModel = viewModel()
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SectionTitle(icon = Icons.Outlined.RestartAlt, text = "Reset Celebration Settings")
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilledTonalButton(
                onClick = { viewModel.resetCelebrationConfirmationDialog.value = true },
            ) {
                Text(text = "Reset Celebrated Birthdays")
            }
        }
    }
}