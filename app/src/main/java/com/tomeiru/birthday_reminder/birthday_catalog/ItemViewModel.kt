package com.tomeiru.birthday_reminder.birthday_catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday

class ItemViewModel(private val birthdayRepository: BirthdayRepository) : ViewModel() {
    var birthdayInDeletionConfirmation: Birthday? by mutableStateOf(null)

    suspend fun setCelebrated(id: Long, celebrated: Boolean) {
        birthdayRepository.setCelebrated(id, celebrated)
    }

    suspend fun deleteBirthday(birthday: Birthday) {
        birthdayRepository.deleteBirthday(birthday)
    }
}