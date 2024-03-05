package com.tomeiru.birthday_reminder.ui

import android.view.Gravity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogWindowProvider

@Composable
fun EntryDialog(
    innerPadding: PaddingValues,
    onDismissRequest: () -> Unit,
    onSingleEntryClick: () -> Unit,
    onBatchImportClick: () -> Unit,
    onBatchImportHelpClick: () -> Unit,
) {
    val content =
        Dialog(onDismissRequest = onDismissRequest) {
            (LocalView.current.parent as DialogWindowProvider).window.setGravity(Gravity.BOTTOM or Gravity.END)
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text(
                        text = "Batch import help",
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    SmallFloatingActionButton(
                        onClick = { onBatchImportHelpClick() },
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.HelpOutline,
                            contentDescription = "Batch import help"
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp)
                ) {
                    Text(
                        text = "Batch import",
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    SmallFloatingActionButton(
                        onClick = { onBatchImportClick() },
                    ) {
                        Icon(Icons.Outlined.UploadFile, contentDescription = "Batch import")
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Single birthday",
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    FloatingActionButton(
                        onClick = { onSingleEntryClick() },
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        contentColor = MaterialTheme.colorScheme.primaryContainer,
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Birthday")
                    }
                }
            }
        }
}