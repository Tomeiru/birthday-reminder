package com.tomeiru.birthday_reminder.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun UpcomingBirthdayList(today: LocalDate, birthdays: List<Birthday>) {
    val upcomingBirthdaysLimit = today.plusMonths(1)
    if (birthdays.isEmpty()) {
        Text(
            text = "No one celebrates their birthday from today to ${
                upcomingBirthdaysLimit.format(
                    DateTimeFormatter.ofPattern("EEEE, MMMM d")
                )
            }!",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        return
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(birthdays) { birthday ->
            BirthdayItem(birthday = birthday, today = today)
        }
    }
}