package com.tomeiru.birthday_reminder.batch_import.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.batch_import.BatchImportViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatchImportTopBar(
    onNavigationIconClick: () -> Unit,
    viewModel: BatchImportViewModel = viewModel()
) {
    val scope = rememberCoroutineScope();
    TopAppBar(
        title = {
            Text(
                text = "Batch Import",
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back to main menu"
                )
            }
        },
        actions = {
            FilledTonalButton(onClick = {
                scope.launch {
                    val validationOutput = viewModel.validationOutput.value ?: return@launch
                    async {
                        viewModel.radioButtonState.value.function(
                            validationOutput
                        )
                    }.await()
                    onNavigationIconClick()
                }
            }) {
                Text(text = "Import")
            }
        }
    )
}