package com.tomeiru.birthday_reminder.birthday_catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.SentimentVeryDissatisfied
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.homepage.BirthdayItem
import java.time.MonthDay
import java.time.Year

@Composable
fun getCelebrationIcon(
    celebrated: Boolean,
    isBirthdayToday: Boolean,
    isBirthdayPassed: Boolean
): ImageVector {
    if (celebrated) {
        return Icons.Outlined.SentimentVerySatisfied
    }
    if (isBirthdayPassed) return Icons.Outlined.SentimentVeryDissatisfied
    if (isBirthdayToday) return Icons.Outlined.Cake
    return Icons.Outlined.HourglassEmpty
}

@Composable
fun CatalogItem(birthday: Birthday, today: MonthDay, year: Year) {
    val monthDay = MonthDay.of(birthday.month, birthday.day)
    val isBirthdayPassed = monthDay.isBefore(today)
    val isBirthdayToday = monthDay == today
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            getCelebrationIcon(birthday.celebrated, isBirthdayToday, isBirthdayPassed),
            "celebration_state",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        BirthdayItem(birthday = birthday)
    }
}