package com.tomeiru.birthday_reminder.birthday_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.data.database.birthday.getAge
import com.tomeiru.birthday_reminder.data.database.birthday.getCelebratedIcon
import com.tomeiru.birthday_reminder.data.database.birthday.getCelebratedText
import com.tomeiru.birthday_reminder.data.database.birthday.getFormattedDate
import java.time.LocalDate

fun getCelebratedText(days: Long, celebrated: Boolean): String {
    if (days > 0) return "Happens in $days!"
    if (days == 0L) {
        if (celebrated) return "Celebrated today!"
        return "It's today! There is still time!"
    }
    return "${if (celebrated) "Celebrated" else "Missed"} ${-days} ago!"
}

@Composable
fun ReadOnlyTextField(
    value: String = "",
    label: @Composable () -> Unit = {},
    leadingIcon: @Composable () -> Unit = {},
) {
    OutlinedTextField(
        readOnly = true,
        onValueChange = {},
        label = label,
        leadingIcon = leadingIcon,
        value = value,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun DetailsDisplay(
    birthday: Birthday,
    today: LocalDate
) {
    val age = birthday.getAge(today)
    Column(
    ) {
        ReadOnlyTextField(
            label = { Text("Name") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Name") },
            value = birthday.name,
        )
        if (age != null) {
            ReadOnlyTextField(
                label = { Text("Age") },
                leadingIcon = { Icon(Icons.Filled.Timer, contentDescription = "Age") },
                value = "$age years old",
            )
        }
        Divider(modifier = Modifier.padding(bottom = 16.dp))
        ReadOnlyTextField(
            label = { Text("Birthday") },
            leadingIcon = { Icon(Icons.Filled.Cake, contentDescription = "Name") },
            value = birthday.getFormattedDate()
        )
        ReadOnlyTextField(
            label = { Text("Celebrated?") },
            leadingIcon = { Icon(birthday.getCelebratedIcon(today), contentDescription = "Name") },
            value = birthday.getCelebratedText(today)
        )
    }

}