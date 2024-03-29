package com.tomeiru.birthday_reminder

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tomeiru.birthday_reminder.birthday_catalog.CatalogViewModel
import com.tomeiru.birthday_reminder.birthday_catalog.ItemViewModel
import com.tomeiru.birthday_reminder.birthday_entry.BirthdayFormViewModel
import com.tomeiru.birthday_reminder.homepage.HomepageViewModel
import com.tomeiru.birthday_reminder.reset_celebrated.ResetCelebratedViewModel
import com.tomeiru.birthday_reminder.today_birthday_page.ui.TodayBirthdayViewModel
import com.tomeiru.birthday_reminder.today_birthday_page.ui.TodayItemViewModel

data class BirthdayFormStartingValues(
    val name: String = "",
    val day: String = "",
    val month: Int? = null,
    val year: Int? = null,
    val celebrated: Boolean = false,
    val id: Long? = null
)

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomepageViewModel(
                this.birthdayReminderApplication().container.birthdayRepository,
                this.birthdayReminderApplication().container.today
            )
        }
        initializer {
            TodayBirthdayViewModel(
                this.birthdayReminderApplication().container.birthdayRepository,
                this.birthdayReminderApplication().container.today
            )
        }
        initializer {
            CatalogViewModel(this.birthdayReminderApplication().container.birthdayRepository)
        }
        initializer {
            ResetCelebratedViewModel(
                this.birthdayReminderApplication().container.birthdayRepository,
                this.birthdayReminderApplication().container.preferenceRepository
            )
        }
        initializer {
            ItemViewModel(
                this.birthdayReminderApplication().container.birthdayRepository
            )
        }
        initializer {
            TodayItemViewModel(
                this.birthdayReminderApplication().container.birthdayRepository
            )
        }
    }

    var formStartingValues = BirthdayFormStartingValues()

    fun setFormDefaultValues(startingValues: BirthdayFormStartingValues) {
        formStartingValues = startingValues
    }


    fun getFormFactory(): ViewModelProvider.Factory {
        return viewModelFactory {
            initializer {
                BirthdayFormViewModel(
                    this.birthdayReminderApplication().container.birthdayRepository,
                    this.birthdayReminderApplication().container.today,
                    formStartingValues.name,
                    formStartingValues.day,
                    formStartingValues.month,
                    formStartingValues.year,
                    formStartingValues.celebrated,
                    formStartingValues.id
                )
            }
        }
    }
}

fun CreationExtras.birthdayReminderApplication() =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BirthdayReminderApplication