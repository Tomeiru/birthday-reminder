package com.tomeiru.birthday_reminder.birthday_catalog

import android.content.Intent
import android.os.Bundle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider
import com.tomeiru.birthday_reminder.birthday_entry.BirthdayEntryActivity
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import kotlinx.coroutines.launch

@Composable
fun ItemDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    birthday: Birthday,
    viewModel: ItemViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope();
    DropdownMenu(expanded = expanded, onDismissRequest = { onDismissRequest() }) {
        if (birthday.celebrated) {
            DropdownMenuItem(leadingIcon = {
                Icon(
                    Icons.Outlined.Close,
                    "set_non_celebrated_icon",
                )
            }, text = { Text(text = "Set as Non-Celebrated") }, onClick = {
                scope.launch {
                    viewModel.setCelebrated(birthday.id, false)
                }
            })
        } else {
            DropdownMenuItem(leadingIcon = {
                Icon(
                    Icons.Outlined.Check,
                    "set_celebrated_icon",
                )
            }, text = { Text(text = "Set as Celebrated") }, onClick = {
                scope.launch {
                    viewModel.setCelebrated(birthday.id, true)
                }
            })
        }
        DropdownMenuItem(leadingIcon = {
            Icon(
                Icons.Outlined.Edit,
                "edit_icon",
            )
        }, text = { Text(text = "Edit") }, onClick = {
            val intent = Intent(context, BirthdayEntryActivity::class.java)
            val bundle = Bundle()
            bundle.putBoolean("edit", true)
            bundle.putString("name", birthday.name)
            bundle.putString("day", birthday.day.toString())
            bundle.putInt("month", birthday.month)
            bundle.putInt("year", birthday.year ?: 0)
            bundle.putLong("id", birthday.id)
            intent.putExtras(bundle)
            context.startActivity(intent)
        })
        DropdownMenuItem(leadingIcon = {
            Icon(
                Icons.Outlined.DeleteForever,
                "delete_icon",
            )
        }, text = { Text(text = "Delete") }, onClick = {
            viewModel.birthdayInDeletionConfirmation = birthday
            onDismissRequest()
        })
    }
}