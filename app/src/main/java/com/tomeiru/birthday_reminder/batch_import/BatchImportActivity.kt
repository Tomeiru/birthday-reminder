package com.tomeiru.birthday_reminder.batch_import

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.tomeiru.birthday_reminder.birthday_catalog.ConfirmationDialog
import com.tomeiru.birthday_reminder.ui.theme.BirthdayReminderTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class BatchImportActivity : ComponentActivity() {
    private val viewModel: BatchImportViewModel by viewModels()

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
                        ConfirmationDialog(
                            title = "This will reset all your birthdays.",
                            showPopup = viewModel.confirmationPopup.value,
                            content = {
                                Text(
                                    text = "Are you sure?",
                                    fontSize = 12.sp,
                                    lineHeight = 20.sp,
                                )
                            },
                            action = {
                                runBlocking {
                                    val validationOutputState =
                                        viewModel.validationOutput.value ?: return@runBlocking
                                    async {
                                        viewModel.radioButtonState.value.function(
                                            validationOutputState
                                        )
                                    }.await()
                                }
                                this@BatchImportActivity.onBackPressedDispatcher.onBackPressed()
                            },
                            onPopupDismiss = {
                                viewModel.confirmationPopup.value = false
                            }
                        )
                    }
                }
            }
        }
    }
}