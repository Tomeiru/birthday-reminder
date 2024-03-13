package com.tomeiru.birthday_reminder.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomeiru.birthday_reminder.preferences.INFORMATIVE_NOTIFICATION_ENABLED
import com.tomeiru.birthday_reminder.preferences.INFORMATIVE_NOTIFICATION_HOURS
import com.tomeiru.birthday_reminder.preferences.INFORMATIVE_NOTIFICATION_MINUTES
import com.tomeiru.birthday_reminder.preferences.REMINDER_NOTIFICATION_ENABLED
import com.tomeiru.birthday_reminder.preferences.REMINDER_NOTIFICATION_HOURS
import com.tomeiru.birthday_reminder.preferences.REMINDER_NOTIFICATION_MINUTES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    data class NotificationPreferenceKeys(
        val enabled: Preferences.Key<Boolean>,
        val hours: Preferences.Key<Int>,
        val minutes: Preferences.Key<Int>,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    val timePickerState = mutableStateOf(
        TimePickerState(
            initialHour = 0,
            initialMinute = 0,
            is24Hour = false,
        )
    )
    val keysUsingTimePickerState: MutableState<NotificationPreferenceKeys?> = mutableStateOf(null)

    val keys = listOf(
        NotificationPreferenceKeys(
            INFORMATIVE_NOTIFICATION_ENABLED,
            INFORMATIVE_NOTIFICATION_HOURS,
            INFORMATIVE_NOTIFICATION_MINUTES
        ),
        NotificationPreferenceKeys(
            REMINDER_NOTIFICATION_ENABLED,
            REMINDER_NOTIFICATION_HOURS,
            REMINDER_NOTIFICATION_MINUTES
        )
    )

    val preferences = dataStore.data.stateIn(
        scope = this.viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null,
    )

    suspend fun setAlarmEnableStatus(key: Preferences.Key<Boolean>, enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = enabled
        }
    }

    suspend fun setAlarmTime(
        keyHour: Preferences.Key<Int>,
        keyMinutes: Preferences.Key<Int>,
        time: LocalTime
    ) {
        dataStore.edit { preferences ->
            preferences[keyHour] = time.hour
            preferences[keyMinutes] = time.minute
        }
    }

}