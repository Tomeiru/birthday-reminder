package com.tomeiru.birthday_reminder.reset_celebrated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.preferences.DISMISSED_POPUP_THIS_YEAR
import com.tomeiru.birthday_reminder.preferences.PreferenceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ResetCelebratedViewModel(
    val birthdayRepository: BirthdayRepository,
    val preferenceRepository: PreferenceRepository
) : ViewModel() {
    val showDialog: StateFlow<Boolean> =
        preferenceRepository.getValue(DISMISSED_POPUP_THIS_YEAR).map { value ->
            value ?: false
        }
            .stateIn(
                scope = this.viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )

    suspend fun resetAllCelebratedBirthdays() {
        birthdayRepository.resetCelebratedBirthdays()
    }

    suspend fun dismissPopup() {
        preferenceRepository.setValue(DISMISSED_POPUP_THIS_YEAR, true)
    }
}