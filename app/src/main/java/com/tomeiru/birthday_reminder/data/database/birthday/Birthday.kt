package com.tomeiru.birthday_reminder.data.database.birthday

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.MonthDay

@Entity(
    tableName = "birthdays",
    indices = [Index(value = ["birthday_name"], unique = true)]
)
data class Birthday(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "birthday_id")
    val id: Long,

    @ColumnInfo(name = "birthday_name")
    val name: String,

    @ColumnInfo(name = "birthday_day")
    val day: Int,

    @ColumnInfo(name = "birthday_month")
    val month: Int,

    @ColumnInfo(name = "birthday_year")
    val year: Int?,

    @ColumnInfo(name = "birthday_celebrated")
    val celebrated: Boolean,
) {
    constructor(name: String, date: MonthDay, celebrated: Boolean) : this(
        0L,
        name,
        date.dayOfMonth,
        date.monthValue,
        null,
        celebrated
    )

    constructor(name: String, date: LocalDate, celebrated: Boolean) : this(
        0L,
        name,
        date.dayOfMonth,
        date.monthValue,
        date.year,
        celebrated
    )
}
