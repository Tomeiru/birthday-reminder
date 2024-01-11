package com.tomeiru.birthday_reminder.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider

@Composable
fun Homepage(
    viewModel: HomepageViewModel = viewModel(factory = ViewModelProvider.Factory),
) {
    val state = viewModel.state.collectAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        HomepageCard("Today", content = {
            TodayInformation(viewModel.today, state.value.todayBirthdays)
        })
        HomepageCard("Upcoming Birthdays", content = {
            UpcomingBirthdayList(viewModel.today, state.value.upcomingBirthdays)
        })
    }
}