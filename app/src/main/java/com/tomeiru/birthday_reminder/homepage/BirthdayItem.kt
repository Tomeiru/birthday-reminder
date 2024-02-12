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
import java.time.Clock
import java.time.MonthDay
import java.time.Year
import java.time.format.DateTimeFormatter

@Composable
fun BirthdayItem(birthday: Birthday) {
    val year = Year.now((Clock.systemDefaultZone()))
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
                    text = "${year.value - birthday.year} years old",
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