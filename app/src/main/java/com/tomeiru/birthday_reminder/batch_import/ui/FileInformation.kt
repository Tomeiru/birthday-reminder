package com.tomeiru.birthday_reminder.batch_import.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.tomeiru.birthday_reminder.batch_import.BirthdayCSV

@Composable
fun FileInformation(validationOutput: BirthdayCSV.ValidationOutput) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = "${validationOutput.correctLines.size} correct lines and ${validationOutput.wrongLines.size} incorrect lines")
        if (validationOutput.wrongLines.size != 0) {
            Text(text = "Incorrect lines are: ${validationOutput.wrongLines}")
            Text(text = validationOutput.wrongLines.joinToString(", "))
        }
    }
}