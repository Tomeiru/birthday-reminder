package com.tomeiru.birthday_reminder.birthday_entry

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
import com.tomeiru.birthday_reminder.ViewModelProvider
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayEntryTopBar(
    onNavigationIconClick: () -> Unit,
    edit: Boolean,
    viewModel: BirthdayFormViewModel = viewModel(factory = ViewModelProvider.getFormFactory()),
) {
    val scope = rememberCoroutineScope();
    val action = if (edit) "Edit" else "Add";
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back to main menu"
                )
            }
        },
        title = {
            Text(
                text = "$action birthday",
            )
        },
        actions = {
            FilledTonalButton(onClick = {
                scope.launch {
                    if (viewModel.addBirthday()) onNavigationIconClick()
                }
            }) {
                Text(text = action)
            }
        }
    )
}