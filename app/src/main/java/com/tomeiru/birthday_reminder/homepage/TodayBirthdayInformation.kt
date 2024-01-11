package com.tomeiru.birthday_reminder.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TodayInformation(today: LocalDate, todayBirthdays: List<Birthday>) {
    val wished = todayBirthdays.size;
    val total = todayBirthdays.size;
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text = today.format(DateTimeFormatter.ofPattern("EEEE, MMMM d")),
                fontSize = 24.sp
            )
            Text(
                text = if (total == 0) "No one celebrates their birthday today \uD83D\uDE22"
                else "$total people celebrate their birthday \uD83C\uDF89 !",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (total > 0) {
            Box {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$wished/$total",
                    )
                    Text(
                        text = "birthdays",
                        fontSize = 8.sp
                    )
                    Text(
                        text = "wished",
                        fontSize = 8.sp
                    )
                }
                CircularProgressIndicator(
                    progress = (wished.toFloat() / total),
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                )
            }
        }
    }

}