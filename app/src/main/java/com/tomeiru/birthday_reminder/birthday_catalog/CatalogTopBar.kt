package com.tomeiru.birthday_reminder.birthday_catalog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: CatalogViewModel = viewModel(factory = ViewModelProvider.Factory),
) {
    val state = viewModel.state.collectAsState()
    LargeTopAppBar(
        title = {
            Column {
                Text(
                    text = "Your Birthdays",
                    fontSize = 32.sp,
                )
                Text(
                    text = "${state.value.nbBirthdays} birthdays",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}