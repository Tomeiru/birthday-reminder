package com.tomeiru.birthday_reminder.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import java.time.Clock
import java.time.LocalDate
import java.time.Month
import java.time.MonthDay
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun UpcomingBirthdayItem(birthday: Birthday) {
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

@Composable
// https://developer.android.com/jetpack/compose/lists -> implementation of LazyList (replacement of RecyclerView)
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
            UpcomingBirthdayItem(birthday = birthday)
        }
    }
}