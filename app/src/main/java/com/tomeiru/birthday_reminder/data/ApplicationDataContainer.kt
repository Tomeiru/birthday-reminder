package com.tomeiru.birthday_reminder.data

import android.content.Context
import com.tomeiru.birthday_reminder.data.database.BirthdayDatabase

class ApplicationDataContainer(private val context: Context) : ApplicationContainer {
    override val birthdayRepository: BirthdayRepository by lazy {
        OfflineBirthdayRepository(BirthdayDatabase.getInstance(context).birthdayDao())
    }
}