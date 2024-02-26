package com.tomeiru.birthday_reminder.data.database.birthday

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.SentimentVeryDissatisfied
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDate
import java.time.MonthDay
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun Birthday.getAge(today: LocalDate): Int? {
    if (year == null) return null
    val birthday = MonthDay.of(month, day)
    if (MonthDay.from(today) < birthday) return today.year - year - 1
    return today.year - year
}

fun Birthday.getFormattedMonthDay(): String {
    return MonthDay.of(month, day)
        .format(DateTimeFormatter.ofPattern("MMMM d"))
}

fun Birthday.getFormattedDate(): String {
    if (year == null) return this.getFormattedMonthDay()
    return LocalDate.of(year, month, day)
        .format(DateTimeFormatter.ofPattern("MMMM d, y"))
}

fun Birthday.getCelebratedText(today: LocalDate): String {
    val days = ChronoUnit.DAYS.between(today, this.toMonthDay().atYear(today.year))
    if (days > 0) return "Happens in $days days!"
    if (days == 0L) {
        if (celebrated) return "Celebrated today!"
        return "It's today! There is still time!"
    }
    return "${if (celebrated) "Celebrated" else "Missed"} it ${-days} days ago!"
}

fun Birthday.getCelebratedIcon(today: LocalDate): ImageVector {
    val days = ChronoUnit.DAYS.between(today, this.toMonthDay().atYear(today.year))
    if (days > 0) return Icons.Outlined.HourglassEmpty
    if (days == 0L) {
        return Icons.Outlined.Celebration
    }
    if (celebrated) return Icons.Outlined.SentimentVerySatisfied
    return Icons.Outlined.SentimentVeryDissatisfied
}

fun Birthday.toMonthDay(): MonthDay {
    return MonthDay.of(month, day)
}

fun Birthday.toLocalDate(): LocalDate? {
    if (year == null) return null
    return LocalDate.of(year, month, day)
}