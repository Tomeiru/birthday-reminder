package com.tomeiru.birthday_reminder

import android.app.Application
import com.tomeiru.birthday_reminder.data.ApplicationContainer
import com.tomeiru.birthday_reminder.data.ApplicationDataContainer
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.database.BirthdayDatabase

class BirthdayReminderApplication : Application() {
    lateinit var container: ApplicationContainer;

    override fun onCreate() {
        super.onCreate()
        container = ApplicationDataContainer(this)
    }
}