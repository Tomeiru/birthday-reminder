package com.tomeiru.birthday_reminder.data

import android.content.Context
import com.tomeiru.birthday_reminder.data.database.BirthdayDatabase
import com.tomeiru.birthday_reminder.preferences.PreferenceRepository
import com.tomeiru.birthday_reminder.preferences.dataStore
import java.time.Clock
import java.time.LocalDate

class ApplicationDataContainer(private val context: Context) : ApplicationContainer {
    override val birthdayRepository: BirthdayRepository by lazy {
        OfflineBirthdayRepository(BirthdayDatabase.getInstance(context).birthdayDao())
    }

    override val preferenceRepository: PreferenceRepository by lazy {
        PreferenceRepository(context.dataStore)
    }
    override val today: LocalDate
        get() = LocalDate.now(Clock.systemDefaultZone())
}