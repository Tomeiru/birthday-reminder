package com.tomeiru.birthday_reminder.birthday_entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider
import java.time.Year

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownMenuBox(
    state: DropdownMenuState<T>,
    onSelect: (Int) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    label: @Composable() (() -> Unit),
    modifier: Modifier = Modifier
) {
    key(state) {
        ExposedDropdownMenuBox(
            expanded = state.expanded,
            onExpandedChange = {
                if (state.enabled) {
                    onExpandedChange(!state.expanded)
                }
            },

            modifier = modifier
        ) {
            OutlinedTextField(
                readOnly = true,
                value = if (state.selected == null) "" else state.formatFunction(state.options[state.selected]),
                onValueChange = {},
                label = label,
                trailingIcon = {
                    TrailingIcon(expanded = state.expanded)
                },
                isError = state.error.isNotEmpty(),
                supportingText = {
                    if (state.error.isNotEmpty()) Text(text = state.error)
                },
                modifier = Modifier.menuAnchor(),
                enabled = state.enabled
            )
            ExposedDropdownMenu(
                expanded = state.expanded,
                onDismissRequest = {
                    onExpandedChange(!state.expanded)
                },
            ) {
                LazyColumn(
                    modifier = Modifier
                        .height(200.dp)
                        .width(500.dp)
                ) {
                    itemsIndexed(state.options) { index, option ->
                        DropdownMenuItem(
                            text = { Text(state.formatFunction(option)) },
                            onClick = {
                                onSelect(index)
                            }
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayEntryForm(
    viewModel: BirthdayFormViewModel = viewModel(factory = ViewModelProvider.Factory),
) {
    Column {
        OutlinedTextField(
            value = viewModel.nameState.text,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("Name") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Name") },
            singleLine = true,
            isError = viewModel.nameState.error.isNotEmpty(),
            trailingIcon = {
                if (viewModel.nameState.error.isNotEmpty())
                    Icon(Icons.Filled.Clear, "error", tint = MaterialTheme.colorScheme.error)
            },
            supportingText = {
                if (viewModel.nameState.error.isNotEmpty()) Text(text = viewModel.nameState.error)
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Divider(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 10.dp, bottom = 16.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(
                value = viewModel.dayState.text,
                onValueChange = { viewModel.updateDay(it) },
                label = { Text("Day") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(140.dp),
                isError = viewModel.dayState.error.isNotEmpty(),
                trailingIcon = {
                    if (viewModel.dayState.error.isNotEmpty())
                        Icon(Icons.Filled.Clear, "error", tint = MaterialTheme.colorScheme.error)
                },
                supportingText = {
                    if (viewModel.dayState.error.isNotEmpty()) Text(text = viewModel.dayState.error)
                }
            )
            DropdownMenuBox(state = viewModel.monthState,
                onSelect = {
                    viewModel.monthState =
                        viewModel.monthState.copy(expanded = false, selected = it, error = "")
                },
                onExpandedChange = {
                    viewModel.monthState = viewModel.monthState.copy(expanded = it)
                },
                label = { Text(text = "Month") })
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DropdownMenuBox<Year>(
                state = viewModel.yearState,
                onSelect = {
                    viewModel.yearState =
                        viewModel.yearState.copy(expanded = false, selected = it, error = "")
                },
                onExpandedChange = {
                    viewModel.yearState = viewModel.yearState.copy(expanded = it)
                },
                label = { Text(text = "Year") },
                modifier = Modifier.width(140.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                    Checkbox(
                        checked = viewModel.yearState.enabled,
                        onCheckedChange = {
                            viewModel.yearState = viewModel.yearState.copy(enabled = it)
                        })
                }
                Text("Include year", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Divider(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 10.dp, bottom = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Checkbox(
                checked = viewModel.celebratedThisYearState,
                onCheckedChange = {
                    viewModel.celebratedThisYearState = it
                })
            Text("Celebrated this year", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}