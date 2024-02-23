package com.tomeiru.birthday_reminder.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.data.database.birthday.getAge
import java.time.LocalDate
import java.time.MonthDay
import java.time.format.DateTimeFormatter

@Composable
fun BirthdayItem(birthday: Birthday, today: LocalDate) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = birthday.name,
                fontSize = 16.sp
            )
            if (birthday.year != null) {
                Text(
                    text = "${birthday.getAge(today)} years old",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            text = MonthDay.of(birthday.month, birthday.day)
                .format(DateTimeFormatter.ofPattern("MMMM d"))
        )
    }
}