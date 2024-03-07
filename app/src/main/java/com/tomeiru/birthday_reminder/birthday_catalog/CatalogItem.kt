package com.tomeiru.birthday_reminder.birthday_catalog

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider
import com.tomeiru.birthday_reminder.birthday_details.BirthdayDetailsActivity
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.data.database.birthday.getCelebratedIcon
import com.tomeiru.birthday_reminder.homepage.BirthdayItem
import java.time.LocalDate
import java.time.MonthDay
import java.time.Year

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogItem(
    birthday: Birthday, today: MonthDay, year: Year,
    viewModel: CatalogViewModel = viewModel(factory = ViewModelProvider.Factory),
) {
    val context = LocalContext.current
    var isDropdownExpanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.combinedClickable(
            onClick = {
                val intent = Intent(context, BirthdayDetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putLong("id", birthday.id)
                intent.putExtras(bundle)
                context.startActivity(intent)
            },
            onLongClick = { isDropdownExpanded = true }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                birthday.getCelebratedIcon(today.atYear(year.value)),
                "celebration_state",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            BirthdayItem(
                birthday = birthday,
                today = LocalDate.of(year.value, today.month, today.dayOfMonth)
            )
        }
        ItemDropdownMenu(
            isDropdownExpanded,
            birthday = birthday,
            onDismissRequest = { isDropdownExpanded = false },
            onDeleteClick = {
                viewModel.currentDeletionConfirmation.value = birthday
            }
        )
    }
}