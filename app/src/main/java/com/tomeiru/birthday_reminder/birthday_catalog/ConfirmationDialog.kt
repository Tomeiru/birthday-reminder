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

@Composable
fun ConfirmationDialog(
    title: String,
    content: @Composable () -> Unit = {},
    action: () -> Unit = {},
    showPopup: Boolean,
    onPopupDismiss: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    if (!showPopup) return
    Dialog(onDismissRequest = { onPopupDismiss() }) {
        Card() {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = title, fontSize = 24.sp)
                content()
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            action()
                            onPopupDismiss()
                        }
                    ) {
                        Text("I'm sure!")
                    }
                    TextButton(
                        onClick = {
                            onPopupDismiss()
                        }
                    ) {
                        Text("Wait, let me reconsider!")
                    }
                }
            }
        }
    }
}