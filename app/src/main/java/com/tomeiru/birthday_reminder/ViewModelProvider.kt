package com.tomeiru.birthday_reminder

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tomeiru.birthday_reminder.birthday_catalog.CatalogViewModel
import com.tomeiru.birthday_reminder.birthday_entry.BirthdayFormViewModel
import com.tomeiru.birthday_reminder.homepage.HomepageViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomepageViewModel(this.birthdayReminderApplication().container.birthdayRepository)
        }
        initializer {
            CatalogViewModel(this.birthdayReminderApplication().container.birthdayRepository)
        }
        initializer {
            BirthdayFormViewModel(this.birthdayReminderApplication().container.birthdayRepository)
        }
    }

}

fun CreationExtras.birthdayReminderApplication() =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BirthdayReminderApplication