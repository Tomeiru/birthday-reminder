package com.tomeiru.birthday_reminder.data

import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.data.database.birthday.BirthdayDao
import kotlinx.coroutines.flow.Flow

interface BirthdayRepository {
    fun getAllBirthdays(): Flow<List<Birthday>>
    fun getBirthdaysByDate(day: Int, month: Int): Flow<List<Birthday>>
    fun getUpcomingBirthdays(day: Int, month: Int): Flow<List<Birthday>>

    suspend fun insertBirthdays(vararg birthday: Birthday)
    suspend fun deleteBirthday(vararg birthday: Birthday)

    suspend fun updateBirthdayName(id: Long, name: String)
    suspend fun updateBirthdayDate(id: Long, day: Int, month: Int, year: Int)
    suspend fun updateBirthday(id: Long, name: String, day: Int, month: Int, year: Int)
}
