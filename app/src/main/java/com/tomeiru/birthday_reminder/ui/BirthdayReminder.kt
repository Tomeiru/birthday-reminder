package com.tomeiru.birthday_reminder.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tomeiru.birthday_reminder.birthday_catalog.BirthdayCatalog
import com.tomeiru.birthday_reminder.birthday_catalog.CatalogTopBar
import com.tomeiru.birthday_reminder.birthday_entry.BirthdayAdderActivity
import com.tomeiru.birthday_reminder.homepage.Homepage
import com.tomeiru.birthday_reminder.homepage.HomepageTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayReminder() {
    val context = LocalContext.current
    var screen by remember { mutableIntStateOf(0) }
    val scrollBehavior = enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            when (screen) {
                0 -> HomepageTopBar()
                1 -> CatalogTopBar(scrollBehavior)
                2 -> HomepageTopBar()
            }
        },
        bottomBar = {
            if (screen < 3) {
                BottomNavigationBar(screen) {
                    screen = it
                }
            }
        },
        floatingActionButton = {
            if (screen == 1) {
                FloatingActionButton(onClick = {
                    context.startActivity(Intent(context, BirthdayAdderActivity::class.java))
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Birthday")
                }
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(8.dp)
                .padding(innerPadding),
        ) {
            when (screen) {
                0 -> Homepage()
                1 -> BirthdayCatalog()
            }
        }
    }
}