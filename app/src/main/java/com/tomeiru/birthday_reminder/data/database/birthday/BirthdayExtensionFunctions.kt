package com.tomeiru.birthday_reminder.data.database.birthday

import java.time.LocalDate
import java.time.MonthDay

fun Birthday.getAge(today: LocalDate): Int? {
    if (year == null) return null
    val birthday = MonthDay.of(month, day)
    if (MonthDay.from(today) < birthday) return today.year - year - 1
    return today.year - year
}