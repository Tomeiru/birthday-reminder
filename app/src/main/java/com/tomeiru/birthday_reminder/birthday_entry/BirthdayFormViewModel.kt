package com.tomeiru.birthday_reminder.birthday_entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.tomeiru.birthday_reminder.data.BirthdayRepository
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.DateTimeException
import java.time.LocalDate
import java.time.Month
import java.time.MonthDay
import java.time.Year
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

data class DropdownMenuState<T>(
    val options: List<T> = listOf(),
    val expanded: Boolean = false,
    val selected: Int? = null,
    val enabled: Boolean = true,
    val formatFunction: (T) -> String = { it.toString() },
    val error: String = "",
)

data class TextFieldState(
    val text: String = "",
    val error: String = "",
)

class BirthdayFormViewModel(
    private val birthdayRepository: BirthdayRepository,
    private val today: LocalDate,
    startingName: String,
    startingDay: String,
    startingMonth: Int?,
    startingYear: Int?,
    startingCelebrated: Boolean,
    private val id: Long?,
) : ViewModel() {
    var nameState by mutableStateOf(TextFieldState(text = startingName))
    var dayState by mutableStateOf(TextFieldState(text = startingDay))
    var monthState by mutableStateOf(
        DropdownMenuState(
            Month.entries,
            false,
            if (startingMonth != null) startingMonth - 1 else null,
            formatFunction = {
                it.getDisplayName(
                    TextStyle.FULL, Locale.getDefault()
                )
            })
    )
    private val years = (Year.now().value - 120..Year.now().value).reversed();
    private val startingYearIndex = years.indexOf(startingYear)
    var yearState by mutableStateOf(DropdownMenuState(years.map {
        Year.of(
            it
        )
    }, false, if (startingYearIndex != -1) startingYearIndex else null))
    var celebratedThisYearState by mutableStateOf(startingCelebrated)

    fun updateName(name: String) {
        nameState = nameState.copy(text = name, error = "")
    }

    private fun validateName(name: String): String {
        if (name.trim().isEmpty()) return "Mandatory field"
        return ""
    }

    private fun validateDay(day: String): String {
        if (day.isEmpty()) return "Mandatory field"
        if (!day.all { Character.isDigit(it) }) return "Must be numeric";
        if (day.toInt() == 0 || day.toInt() > 32) return "Between 1-31";
        return ""
    }

    private fun validateDropdownMenus(enabled: Boolean, selected: Int?): String {
        if (!enabled) return ""
        if (selected != null) return ""
        return "Mandatory field"
    }

    fun updateDay(day: String) {
        dayState = dayState.copy(text = day, error = validateDay(day))
    }

    private fun validateForm(): Boolean {
        var isValid = true;
        val validationFunctions: Array<() -> String> = arrayOf(
            { validateName(nameState.text) },
            { validateDay(dayState.text) },
            { validateDropdownMenus(monthState.enabled, monthState.selected) },
            { validateDropdownMenus(yearState.enabled, yearState.selected) },
        )
        val setErrorFunctions: Array<(String) -> Unit> = arrayOf(
            { nameState = nameState.copy(error = it) },
            { dayState = dayState.copy(error = it) },
            { monthState = monthState.copy(error = it) },
            { yearState = yearState.copy(error = it) },
        )
        for (i in 0..<4) {
            val error = validationFunctions[i]();
            setErrorFunctions[i](error);
            if (error.isNotEmpty()) {
                isValid = false;
            }
        }
        return isValid
    }

    private fun transformFormDataToBirthday(
        name: TextFieldState,
        day: TextFieldState,
        month: DropdownMenuState<Month>,
        year: DropdownMenuState<Year>,
        celebrated: Boolean
    ): Birthday {
        if (year.enabled) {
            val date = LocalDate.of(
                year.options[year.selected!!].value,
                month.options[month.selected!!],
                day.text.toInt()
            )
            if (ChronoUnit.DAYS.between(
                    today,
                    date
                ) > 0
            ) throw DateTimeException("Date is in the future")
            return Birthday(name.text.trim(), date, celebrated);
        }
        val date = MonthDay.of(month.options[month.selected!!], day.text.toInt());
        return Birthday(name.text.trim(), date, celebrated);
    }

    suspend fun addBirthday(): Boolean {
        if (!validateForm()) {
            return false
        }
        try {
            val birthday = transformFormDataToBirthday(
                nameState,
                dayState,
                monthState,
                yearState,
                celebratedThisYearState
            );
            val databaseSameNameBirthday = runBlocking {
                birthdayRepository.getBirthdayByName(birthday.name).first()
            }
            if (databaseSameNameBirthday != null && databaseSameNameBirthday.id != id) {
                nameState = nameState.copy(error = "A birthday with the same name already exists")
                return false
            }
            if (id != null) {
                this.birthdayRepository.updateBirthday(
                    id,
                    birthday.name,
                    birthday.day,
                    birthday.month,
                    birthday.year,
                    birthday.celebrated
                )
            } else {
                this.birthdayRepository.insertBirthdays(birthday);
            }
        } catch (e: DateTimeException) {
            val error = "Invalid date"
            if (yearState.enabled) {
                yearState = yearState.copy(error = error);
            }
            dayState = dayState.copy(error = error);
            monthState = monthState.copy(error = error);
            return false
        }
        return true
    }
}