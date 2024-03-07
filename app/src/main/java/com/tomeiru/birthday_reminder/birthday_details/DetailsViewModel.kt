package com.tomeiru.birthday_reminder.birthday_details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: BirthdayRepository,
) : ViewModel() {
    val id: StateFlow<Long> = handle.getStateFlow("id", -1)
    val details: StateFlow<Birthday?> = repository.getBirthdayById(id.value).stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
    val currentDeletionConfirmation: MutableState<Boolean> = mutableStateOf(false)

    fun setId(id: Long) {
        handle["id"] = id
    }

    suspend fun deleteBirthday(birthday: Birthday) {
        repository.deleteBirthday(birthday)
    }
}