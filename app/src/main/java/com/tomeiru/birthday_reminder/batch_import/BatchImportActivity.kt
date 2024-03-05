package com.tomeiru.birthday_reminder.batch_import

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomeiru.birthday_reminder.batch_import.ui.BatchImportTopBar
import com.tomeiru.birthday_reminder.batch_import.ui.FileInformation
import com.tomeiru.birthday_reminder.batch_import.ui.NextStepSelection
import com.tomeiru.birthday_reminder.ui.theme.BirthdayReminderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BatchImportActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras ?: return
        val validationOutput: BirthdayCSV.ValidationOutput =
            bundle.getParcelable("validationOutput") ?: return
        setContent {
            BirthdayReminderTheme {
                Scaffold(
                    topBar = {
                        BatchImportTopBar(
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
                        Column {
                            Text(
                                text = "File information",
                                fontSize = 24.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            FileInformation(validationOutput = validationOutput)
                            Divider(modifier = Modifier.padding(16.dp))
                            Text(
                                text = "Next step",
                                fontSize = 24.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            NextStepSelection()
                        }
                    }
                }
            }
        }
    }
}