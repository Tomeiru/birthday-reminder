package com.tomeiru.birthday_reminder.birthday_catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider
import java.time.Month
import java.time.MonthDay
import java.time.Year
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MonthItemTitle(month: Month) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = month.getDisplayName(TextStyle.FULL, Locale.getDefault()))
        Divider(
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun BirthdayCatalog(
    viewModel: CatalogViewModel = viewModel(factory = ViewModelProvider.Factory),
) {
    val state = viewModel.state.collectAsState()
    if (state.value.nbBirthdays == 0) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "No birthday to show")
            Text(text = "Press \"+\" to add new birthdays")
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            state.value.birthdaysByMonth.forEach { (monthIndex, birthdays) ->
                item {
                    MonthItemTitle(month = Month.of(monthIndex))
                }
                items(birthdays) { birthday ->
                    CatalogItem(
                        birthday = birthday,
                        MonthDay.from(viewModel.today),
                        Year.from(viewModel.today)
                    )
                }
                item {}
            }
        }
    }
    DeletionConfirmationDialog()
}