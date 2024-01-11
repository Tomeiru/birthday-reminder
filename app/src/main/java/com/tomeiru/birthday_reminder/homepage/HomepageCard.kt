package com.tomeiru.birthday_reminder.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomepageCard(title: String, content: @Composable () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .wrapContentSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = title,
                    fontSize = 16.sp
                )
            }
            content()
        }
    }
}