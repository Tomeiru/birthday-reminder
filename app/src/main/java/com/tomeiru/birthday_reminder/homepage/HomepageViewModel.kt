package com.tomeiru.birthday_reminder.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.Clock
import java.time.LocalDate

data class HomepageState(
    val todayBirthdays: List<Birthday> = listOf(),
    val upcomingBirthdays: List<Birthday> = listOf(),
)

class HomepageViewModel(birthdayRepository: BirthdayRepository) : ViewModel() {
    val today: LocalDate = LocalDate.now(Clock.systemDefaultZone())
    val state: StateFlow<HomepageState> =
        birthdayRepository.getBirthdaysByDate(today.dayOfMonth, today.monthValue)
            .combine(
                birthdayRepository.getUpcomingBirthdays(
                    today.dayOfMonth,
                    today.monthValue
                )
            ) { todayBirthdays, upcomingBirthdays ->
                HomepageState(todayBirthdays, upcomingBirthdays)
            }.stateIn(
                scope = this.viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomepageState(),
            )
}