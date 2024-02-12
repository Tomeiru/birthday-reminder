package com.tomeiru.birthday_reminder.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PreferenceRepository(private val dataStore: DataStore<Preferences>) {
    fun <T> getValue(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.map { preferences ->
            Log.d("pref", preferences.toString())
            preferences[key]
        }
    }

    suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}