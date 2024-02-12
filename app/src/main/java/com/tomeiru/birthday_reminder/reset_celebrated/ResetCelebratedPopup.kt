package com.tomeiru.birthday_reminder.reset_celebrated

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider
import kotlinx.coroutines.launch

@Composable
fun ResetCelebratedPopup(
    viewModel: ResetCelebratedViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val scope = rememberCoroutineScope();
    val showDialog = viewModel.showDialog.collectAsState()
    if (!showDialog.value) {
        Dialog(onDismissRequest = {}) {
            Card() {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Happy New Year \uD83C\uDF89!", fontSize = 24.sp)
                    Text(
                        text = "Do you wish to reset the birthdays celebrated this year?",
                        fontSize = 12.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        text = "Note: This action is possible at any time via the settings",
                        fontSize = 12.sp,
                        lineHeight = 20.sp
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = {
                                scope.launch {
                                    viewModel.resetAllCelebratedBirthdays()
                                    viewModel.dismissPopup()
                                }
                            }
                        ) {
                            Text("Yes please!")
                        }
                        TextButton(
                            onClick = {
                                scope.launch {
                                    viewModel.dismissPopup()
                                }
                            }
                        ) {
                            Text("No thanks!")
                        }
                    }
                }
            }
        }
    }
}