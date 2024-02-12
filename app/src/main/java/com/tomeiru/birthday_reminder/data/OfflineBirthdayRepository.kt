package com.tomeiru.birthday_reminder.data

import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.data.database.birthday.BirthdayDao
import kotlinx.coroutines.flow.Flow

class OfflineBirthdayRepository(private val birthdayDao: BirthdayDao) : BirthdayRepository {
    override fun getAllBirthdays(): Flow<List<Birthday>> = birthdayDao.findAllOrderedByDate()
    override fun getBirthdaysByDate(day: Int, month: Int): Flow<List<Birthday>> =
        birthdayDao.findBirthdaysByDate(day, month)

    override fun getUpcomingBirthdays(day: Int, month: Int): Flow<List<Birthday>> =
        birthdayDao.findUpcomingBirthdays(day, month)

    override suspend fun insertBirthdays(vararg birthday: Birthday) =
        birthdayDao.insertAll(*birthday)

    override suspend fun deleteBirthday(vararg birthday: Birthday) = birthdayDao.delete(*birthday)

    override suspend fun updateBirthday(id: Long, name: String, day: Int, month: Int, year: Int) =
        birthdayDao.update(id, name, day, month, year)

    override suspend fun updateBirthdayName(id: Long, name: String) = birthdayDao.update(id, name)
    override suspend fun updateBirthdayDate(id: Long, day: Int, month: Int, year: Int) =
        birthdayDao.update(id, day, month, year)

    override suspend fun resetCelebratedBirthdays() {
        birthdayDao.resetCelebratedBirthdays()
    }
}