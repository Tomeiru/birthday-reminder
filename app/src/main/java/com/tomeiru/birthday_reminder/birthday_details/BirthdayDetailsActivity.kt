package com.tomeiru.birthday_reminder.birthday_details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tomeiru.birthday_reminder.BirthdayReminderApplication
import com.tomeiru.birthday_reminder.birthday_catalog.DeletionConfirmationDialog
import com.tomeiru.birthday_reminder.ui.theme.BirthdayReminderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BirthdayDetailsActivity : ComponentActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras ?: return
        val id = bundle.getLong("id", -1)
        val today = (this.applicationContext as BirthdayReminderApplication).container.today
        viewModel.setId(id)

        setContent {
            BirthdayReminderTheme {
                Scaffold(
                    topBar = {
                        DetailsTopBar(
                            onNavigationIconClick = { this.onBackPressedDispatcher.onBackPressed() }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                            .padding(8.dp)
                            .padding(innerPadding),
                    ) {
                        val state = viewModel.details.collectAsState()
                        state.value?.let {
                            DetailsDisplay(birthday = it, today = today)
                        }
                        DeletionConfirmationDialog(onDelete = {
                            this@BirthdayDetailsActivity.onBackPressedDispatcher.onBackPressed()
                        })
                    }
                }
            }

        }
    }
}