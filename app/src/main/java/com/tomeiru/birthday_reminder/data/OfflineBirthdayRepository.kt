package com.tomeiru.birthday_reminder.data

import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.data.database.birthday.BirthdayDao
import kotlinx.coroutines.flow.Flow

class OfflineBirthdayRepository(private val birthdayDao: BirthdayDao) : BirthdayRepository {
    override fun getAllBirthdays(): Flow<List<Birthday>> = birthdayDao.findAllOrderedByDate()
    override fun getBirthdaysByDate(day: Int, month: Int): Flow<List<Birthday>> =
        birthdayDao.findBirthdaysByDate(day, month)

    override fun getBirthdayByDateAndName(
        day: Int,
        month: Int,
        name: String
    ): Flow<Birthday?> =
        birthdayDao.findBirthdayByDateAndName(day, month, name)

    override fun getUpcomingBirthdays(day: Int, month: Int): Flow<List<Birthday>> =
        birthdayDao.findUpcomingBirthdays(day, month)

    override fun getBirthdayById(id: Long): Flow<Birthday> =
        birthdayDao.findBirthdayById(id)

    override suspend fun insertBirthdays(vararg birthday: Birthday) =
        birthdayDao.insertAll(*birthday)

    override suspend fun deleteBirthday(vararg birthday: Birthday) = birthdayDao.delete(*birthday)

    override suspend fun updateBirthday(id: Long, name: String, day: Int, month: Int, year: Int?) =
        birthdayDao.update(id, name, day, month, year)

    override suspend fun updateBirthday(
        id: Long,
        name: String,
        day: Int,
        month: Int,
        year: Int?,
        celebrated: Boolean
    ) {
        birthdayDao.update(id, name, day, month, year, celebrated)
    }

    override suspend fun updateBirthdayName(id: Long, name: String) = birthdayDao.update(id, name)
    override suspend fun updateBirthdayDate(id: Long, day: Int, month: Int, year: Int?) =
        birthdayDao.update(id, day, month, year)

    override fun getUncelebratedBirthdaysByDate(day: Int, month: Int): Flow<List<Birthday>> {
        return birthdayDao.getBirthdaysByDateAndCelebration(day, month, false)
    }


    override suspend fun setCelebrated(id: Long, celebrated: Boolean) {
        birthdayDao.update(id, celebrated)
    }

    override suspend fun resetCelebratedBirthdays() {
        birthdayDao.resetCelebratedBirthdays()
    }

}