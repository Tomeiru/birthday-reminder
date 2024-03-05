package com.tomeiru.birthday_reminder.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tomeiru.birthday_reminder.batch_import.BirthdayCSV

@Composable
fun BatchImportText(
    text: String
) {
    Text(
        text = text,
        fontSize = 12.sp,
        lineHeight = 12.sp
    )
}

@Composable
fun BatchImportHelpDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "How to bulk import?", fontSize = 24.sp)
                BatchImportText(
                    text = "The bulk import expects to receive a file in the CSV format with the following specifications:",
                )
                Column {
                    BatchImportText(
                        text = "    - A name field consisting of a single non empty line with no comma",
                    )
                    BatchImportText(
                        text = "    - A date field of format yyyy-mm-dd or mm-dd",
                    )
                    BatchImportText(
                        text = "    - A celebrated field with either \"yes\" or \"no\"",
                    )
                    BatchImportText(
                        text = "    - Each cell is separated by a \"${BirthdayCSV.CELL_SEPARATOR}",
                    )
                    BatchImportText(
                        text = "    - Each line is separated by a \"\\r\\n\"",
                    )
                    BatchImportText(
                        text = "    - Must have the following header and respect its order: \"${BirthdayCSV.HEADER}\"",
                    )
                }
                Divider(
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                BatchImportText(
                    text = "Here's an example:",
                )
                Column {
                    BatchImportText(
                        text = BirthdayCSV.HEADER,
                    )
                    BatchImportText(
                        text = "Mathieu,12-05,no",
                    )
                    BatchImportText(
                        text = "Julien,2004-01-05,yes",
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            onDismissRequest()
                        }
                    ) {
                        Text("Got it!")
                    }
                }
            }
        }
    }
}