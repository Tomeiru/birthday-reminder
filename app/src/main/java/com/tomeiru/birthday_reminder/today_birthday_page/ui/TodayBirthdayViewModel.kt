package com.tomeiru.birthday_reminder.today_birthday_page.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class TodayBirthdayViewModel(birthdayRepository: BirthdayRepository, val today: LocalDate) :
    ViewModel() {
    val birthdays: StateFlow<List<Birthday>> =
        birthdayRepository.getBirthdaysByDate(today.dayOfMonth, today.monthValue).stateIn(
            this.viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )
}