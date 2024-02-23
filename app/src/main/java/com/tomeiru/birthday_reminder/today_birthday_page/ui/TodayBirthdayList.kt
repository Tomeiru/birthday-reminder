package com.tomeiru.birthday_reminder.today_birthday_page.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentVeryDissatisfied
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider

@Composable
fun TodayBirthdayList(
    viewModel: TodayBirthdayViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val birthdays = viewModel.birthdays.collectAsState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(birthdays.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    if (it.celebrated) Icons.Outlined.SentimentVerySatisfied else Icons.Outlined.SentimentVeryDissatisfied,
                    "celebration_state",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TodayBirthdayItem(
                    birthday = it,
                    today = viewModel.today
                )
            }
        }
    }
}