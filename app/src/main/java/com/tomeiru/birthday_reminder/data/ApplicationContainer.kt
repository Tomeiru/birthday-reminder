package com.tomeiru.birthday_reminder.data

import com.tomeiru.birthday_reminder.preferences.PreferenceRepository
import java.time.LocalDate

interface ApplicationContainer {
    val birthdayRepository: BirthdayRepository
    val preferenceRepository: PreferenceRepository
    val today: LocalDate
}