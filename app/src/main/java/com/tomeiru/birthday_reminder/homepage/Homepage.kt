package com.tomeiru.birthday_reminder.homepage

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tomeiru.birthday_reminder.ViewModelProvider
import com.tomeiru.birthday_reminder.today_birthday_page.TodayBirthdayActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homepage(
    viewModel: HomepageViewModel = viewModel(factory = ViewModelProvider.Factory),
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        HomepageCard("Today", content = {
            TodayInformation(viewModel.today, state.value.todayBirthdays)
        }, subContent = {
            if (state.value.todayBirthdays.isNotEmpty()) {
                CompositionLocalProvider(
                    LocalMinimumInteractiveComponentEnforcement provides false
                ) {
                    Surface(
                        onClick = {
                            context.startActivity(
                                Intent(
                                    context,
                                    TodayBirthdayActivity::class.java
                                )
                            )
                        }
                    ) {
                        Text(
                            text = "See the list",
                            style = TextStyle(
                                color = colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                }
            }
        }
        )
        HomepageCard("Upcoming Birthdays", content = {
            UpcomingBirthdayList(viewModel.today, state.value.upcomingBirthdays)
        })
    }
}
