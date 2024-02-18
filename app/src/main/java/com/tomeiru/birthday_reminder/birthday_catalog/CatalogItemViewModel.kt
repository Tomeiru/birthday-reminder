package com.tomeiru.birthday_reminder.birthday_catalog

import androidx.lifecycle.ViewModel
import com.tomeiru.birthday_reminder.data.BirthdayRepository

class CatalogItemViewModel(private val birthdayRepository: BirthdayRepository) : ViewModel() {
    suspend fun setCelebrated(id: Long, celebrated: Boolean) {
        birthdayRepository.setCelebrated(id, celebrated)
    }
}