package com.tomeiru.birthday_reminder.birthday_catalog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.SentimentVeryDissatisfied
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.homepage.BirthdayItem
import kotlinx.coroutines.launch
import java.time.MonthDay
import java.time.Year

@Composable
fun getCelebrationIcon(
    celebrated: Boolean,
    isBirthdayToday: Boolean,
    isBirthdayPassed: Boolean
): ImageVector {
    if (celebrated) {
        return Icons.Outlined.SentimentVerySatisfied
    }
    if (isBirthdayPassed) return Icons.Outlined.SentimentVeryDissatisfied
    if (isBirthdayToday) return Icons.Outlined.Cake
    return Icons.Outlined.HourglassEmpty
}

@Composable
fun CatalogItemDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    id: Long,
    celebrated: Boolean,
    viewModel: CatalogItemViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    val scope = rememberCoroutineScope();
    DropdownMenu(expanded = expanded, onDismissRequest = { onDismissRequest() }) {
        if (celebrated) {
            DropdownMenuItem(leadingIcon = {
                Icon(
                    Icons.Outlined.Close,
                    "set_non_celebrated_icon",
                )
            }, text = { Text(text = "Set as Non-Celebrated") }, onClick = {
                scope.launch {
                    viewModel.setCelebrated(id, false)
                }
                onDismissRequest()
            })
        } else {
            DropdownMenuItem(leadingIcon = {
                Icon(
                    Icons.Outlined.Check,
                    "set_celebrated_icon",
                )
            }, text = { Text(text = "Set as Celebrated") }, onClick = {
                scope.launch {
                    viewModel.setCelebrated(id, true)
                }
                onDismissRequest()
            })
        }
        DropdownMenuItem(leadingIcon = {
            Icon(
                Icons.Outlined.Edit,
                "edit_icon",
            )
        }, text = { Text(text = "Edit") }, onClick = { /*TODO*/ })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogItem(birthday: Birthday, today: MonthDay, year: Year) {
    var isDropdownExpanded by remember { mutableStateOf(false) }
    val monthDay = MonthDay.of(birthday.month, birthday.day)
    val isBirthdayPassed = monthDay.isBefore(today)
    val isBirthdayToday = monthDay == today
    Surface(
        modifier = Modifier.combinedClickable(
            onClick = {},
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
                getCelebrationIcon(birthday.celebrated, isBirthdayToday, isBirthdayPassed),
                "celebration_state",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            BirthdayItem(birthday = birthday)
        }
        CatalogItemDropdownMenu(
            isDropdownExpanded,
            id = birthday.id,
            onDismissRequest = { isDropdownExpanded = false },
            celebrated = birthday.celebrated
        )
    }
}