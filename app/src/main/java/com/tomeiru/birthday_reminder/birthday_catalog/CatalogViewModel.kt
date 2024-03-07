package com.tomeiru.birthday_reminder.birthday_catalog

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.Clock
import java.time.LocalDate

data class CatalogState(
    val nbBirthdays: Int = 0,
    val birthdaysByMonth: Map<Int, List<Birthday>> = mapOf(),
)

class CatalogViewModel(private val birthdayRepository: BirthdayRepository) : ViewModel() {
    val currentDeletionConfirmation: MutableState<Birthday?> = mutableStateOf(null)
    val today: LocalDate = LocalDate.now(Clock.systemDefaultZone())
    val state: StateFlow<CatalogState> = birthdayRepository.getAllBirthdays().map {
        CatalogState(
            it.size,
            it.groupBy { birthday -> birthday.month })
    }
        .stateIn(
            scope = this.viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CatalogState(),
        )

    fun closePopUp() {
        currentDeletionConfirmation.value = null
    }

    suspend fun deleteBirthday(birthday: Birthday) {
        birthdayRepository.deleteBirthday(birthday)
    }
}