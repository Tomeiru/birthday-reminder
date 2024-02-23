package com.tomeiru.birthday_reminder.today_birthday_page.ui

import androidx.lifecycle.ViewModel
import com.tomeiru.birthday_reminder.data.BirthdayRepository

class TodayItemViewModel(private val birthdayRepository: BirthdayRepository) : ViewModel() {
    suspend fun setCelebrated(id: Long, celebrated: Boolean) {
        birthdayRepository.setCelebrated(id, celebrated)
    }
}