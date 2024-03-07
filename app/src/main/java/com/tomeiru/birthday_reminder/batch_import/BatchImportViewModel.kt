package com.tomeiru.birthday_reminder.batch_import

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

//TODO: accept validation output here
@HiltViewModel
class BatchImportViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val repository: BirthdayRepository
) : ViewModel() {
    val confirmationPopup: MutableState<Boolean> = mutableStateOf(false)

    data class RadioButtonInformation(
        val text: String = "",
        val function: suspend (validationOutput: BirthdayCSV.ValidationOutput) -> Unit = {},
        val needsConfirmation: Boolean = false
    )

    private suspend fun deleteAndReplace(validationOutput: BirthdayCSV.ValidationOutput) {
        val birthdays = BirthdayCSV().parseFormat(validationOutput)
        runBlocking {
            val databaseBirthdays = repository.getAllBirthdays().first()
            repository.deleteBirthday(*databaseBirthdays.toTypedArray())
        }
        repository.insertBirthdays(*birthdays.toTypedArray())
    }

    private suspend fun insertOrUpdateBirthday(birthday: Birthday) {
        val birthdayInstance = runBlocking {
            repository.getBirthdayByDateAndName(
                birthday.day,
                birthday.month,
                birthday.name
            ).first()
        } ?: return repository.insertBirthdays(birthday)
        return repository.updateBirthday(
            birthdayInstance.id,
            birthday.name,
            birthday.day,
            birthday.month,
            birthday.year,
            birthday.celebrated
        )

    }

    private suspend fun checkImportedBirthdaysAndUpdate(validationOutput: BirthdayCSV.ValidationOutput) {
        val birthdays = BirthdayCSV().parseFormat(validationOutput)
        for (birthday in birthdays) {
            insertOrUpdateBirthday(birthday)
        }
    }

    val radioButtons = listOf(
        RadioButtonInformation(
            "Check with current data and add/update accordingly (Recommended)",
            ::checkImportedBirthdaysAndUpdate
        ),
        RadioButtonInformation(
            "Delete current data and replace by imported data",
            ::deleteAndReplace,
            true
        ),
    )
    val radioButtonState = mutableStateOf(radioButtons[0])
    val validationOutput: StateFlow<BirthdayCSV.ValidationOutput?> =
        handle.getStateFlow("validationOutput", null)
}