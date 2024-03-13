package com.tomeiru.birthday_reminder.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val LAST_LAUNCHED_YEAR = intPreferencesKey("last_launched_year")

val DISMISSED_POPUP_THIS_YEAR = booleanPreferencesKey("dismissed_popup_this_year")

val INFORMATIVE_NOTIFICATION_ENABLED = booleanPreferencesKey("is_informative_notification_enabled")
val INFORMATIVE_NOTIFICATION_HOURS = intPreferencesKey("informative_notification_hours")
val INFORMATIVE_NOTIFICATION_MINUTES = intPreferencesKey("informative_notification_minute")

val REMINDER_NOTIFICATION_ENABLED = booleanPreferencesKey("is_reminder_notification_enabled")
val REMINDER_NOTIFICATION_HOURS = intPreferencesKey("reminder_notification_hours")
val REMINDER_NOTIFICATION_MINUTES = intPreferencesKey("reminder_notification_minutes")