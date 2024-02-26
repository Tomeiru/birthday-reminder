package com.tomeiru.birthday_reminder.birthday_catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@Composable
fun DeletionConfirmationDialog(
    onDelete: () -> Unit = {},
    viewModel: ItemViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val scope = rememberCoroutineScope()
    if (viewModel.birthdayInDeletionConfirmation !== null) {
        Dialog(onDismissRequest = {
            viewModel.birthdayInDeletionConfirmation = null
        }) {
            Card() {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Delete for all eternity? \uD83D\uDE28", fontSize = 24.sp)
                    Text(
                        text = "Are you sure?",
                        fontSize = 12.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        text = "${viewModel.birthdayInDeletionConfirmation!!.name}'s birthday will be deleted immediately and this action cannot be undone.",
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
                                    onDelete()
                                    async {
                                        viewModel.deleteBirthday(viewModel.birthdayInDeletionConfirmation!!)
                                    }.await()
                                    viewModel.birthdayInDeletionConfirmation = null
                                }
                            }
                        ) {
                            Text("I'm sure!")
                        }
                        TextButton(
                            onClick = {
                                viewModel.birthdayInDeletionConfirmation = null
                            }
                        ) {
                            Text("Wait, let me reconsider!")
                        }
                    }
                }
            }
        }
    }
}