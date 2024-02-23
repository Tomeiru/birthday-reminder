package com.tomeiru.birthday_reminder.today_birthday_page.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider
import com.tomeiru.birthday_reminder.birthday_catalog.CatalogItemViewModel
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.data.database.birthday.getAge
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun TodayBirthdayItem(
    birthday: Birthday,
    today: LocalDate,
    viewModel: CatalogItemViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = birthday.name,
                fontSize = 16.sp
            )
            if (birthday.year != null) {
                Text(
                    text = "${birthday.getAge(today)} years old",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Button(onClick = {
            scope.launch {
                viewModel.setCelebrated(birthday.id, !birthday.celebrated)
            }
        }) {
            Text(text = "Set as ${if (!birthday.celebrated) "Celebrated" else "Non-Celebrated"}")
        }
    }
}