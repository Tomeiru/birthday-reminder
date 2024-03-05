package com.tomeiru.birthday_reminder.ui

import android.util.Pair
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(screen: Int, changeScreen: (Int) -> Unit) {
    val items = listOf(
        Pair("Home", Icons.Filled.Home),
        Pair("Catalog", Icons.AutoMirrored.Filled.List),
        Pair("Settings", Icons.Filled.Settings)
    )
    NavigationBar(
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.second, contentDescription = item.first) },
                label = { Text(item.first) },
                selected = index == screen,
                onClick = { changeScreen(index) },
            )
        }
    }
}