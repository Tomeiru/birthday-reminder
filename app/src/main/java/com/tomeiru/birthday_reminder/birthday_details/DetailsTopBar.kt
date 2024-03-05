package com.tomeiru.birthday_reminder.birthday_details

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.birthday_catalog.ItemDropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTopBar(
    onNavigationIconClick: () -> Unit,
    viewModel: DetailsViewModel = viewModel()
) {
    val state = viewModel.details.collectAsState()
    var isMenuOpen by remember { mutableStateOf(false) }
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back to main menu"
                )
            }
        },
        title = {
            Text(
                text = "${state.value?.name ?: "Person"}'s birthday",
            )
        },
        actions = {
            IconButton(onClick = { isMenuOpen = true }) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "Birthday Menu Icon Button"
                )
            }
            if (state.value != null) {
                ItemDropdownMenu(
                    expanded = isMenuOpen,
                    onDismissRequest = { isMenuOpen = false },
                    birthday = state.value!!
                )
            }
        }
    )
}