package com.tomeiru.birthday_reminder

import DISMISSED_POPUP_THIS_YEAR
import LAST_LAUNCHED_YEAR
import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import com.tomeiru.birthday_reminder.data.ApplicationContainer
import com.tomeiru.birthday_reminder.data.ApplicationDataContainer
import dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.LocalDate

suspend fun onLaunchPreferencesRoutine(today: LocalDate, context: Context) {
    val lastLaunchedYear: Int? = runBlocking { context.dataStore.data.first()[LAST_LAUNCHED_YEAR] }

    if (lastLaunchedYear == null || lastLaunchedYear != today.year) {
        context.dataStore.edit { preferences ->
            preferences[LAST_LAUNCHED_YEAR] = today.year
            preferences[DISMISSED_POPUP_THIS_YEAR] = lastLaunchedYear == null
        }
    }
}

class BirthdayReminderApplication : Application() {
    lateinit var container: ApplicationContainer;

    override fun onCreate() {
        super.onCreate()
        container = ApplicationDataContainer(this)
        runBlocking {
            onLaunchPreferencesRoutine(container.today, this@BirthdayReminderApplication)
        }
    }
}