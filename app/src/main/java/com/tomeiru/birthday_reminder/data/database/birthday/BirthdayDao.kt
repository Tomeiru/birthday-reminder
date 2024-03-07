package com.tomeiru.birthday_reminder.data.database.birthday

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BirthdayDao {
    @Insert
    suspend fun insertAll(vararg birthday: Birthday)

    @Query("SELECT * FROM birthdays WHERE birthday_month LIKE :month AND birthday_day LIKE :day ORDER BY birthday_name")
    fun findBirthdaysByDate(day: Int, month: Int): Flow<List<Birthday>>

    @Query("SELECT * FROM birthdays WHERE birthday_month LIKE :month AND birthday_day LIKE :day AND birthday_name LIKE :name ORDER BY birthday_name")
    fun findBirthdayByDateAndName(day: Int, month: Int, name: String): Flow<Birthday?>

    @Query("SELECT * FROM birthdays ORDER BY birthday_month, birthday_day")
    fun findAllOrderedByDate(): Flow<List<Birthday>>

    @Query("SELECT * FROM birthdays WHERE (birthday_month LIKE :month AND birthday_day > :day) OR (birthday_month LIKE :month + 1 AND birthday_day < :day) ORDER BY birthday_month, birthday_day")
    fun findUpcomingBirthdays(day: Int, month: Int): Flow<List<Birthday>>

    @Query("SELECT * FROM birthdays WHERE birthday_id LIKE :id")
    fun findBirthdayById(id: Long): Flow<Birthday>

    @Query("UPDATE birthdays SET birthday_name = :name WHERE birthday_id = :id")
    suspend fun update(id: Long, name: String)

    @Query("UPDATE birthdays SET birthday_year = :year, birthday_month = :month, birthday_day = :day WHERE birthday_id = :id")
    suspend fun update(id: Long, day: Int, month: Int, year: Int?)

    @Query("UPDATE birthdays SET birthday_name = :name, birthday_year = :year, birthday_month = :month, birthday_day = :day WHERE birthday_id = :id")
    suspend fun update(id: Long, name: String, day: Int, month: Int, year: Int?)

    @Query("UPDATE birthdays SET birthday_name = :name, birthday_year = :year, birthday_month = :month, birthday_day = :day, birthday_celebrated = :celebrated WHERE birthday_id = :id")
    suspend fun update(
        id: Long,
        name: String,
        day: Int,
        month: Int,
        year: Int?,
        celebrated: Boolean
    )

    @Query("UPDATE birthdays SET birthday_celebrated = :celebrated WHERE birthday_id = :id")
    suspend fun update(id: Long, celebrated: Boolean)

    @Query("SELECT * FROM birthdays WHERE birthday_month LIKE :month AND birthday_day LIKE :day AND birthday_celebrated LIKE :celebrated ORDER BY birthday_name")
    fun getBirthdaysByDateAndCelebration(
        day: Int,
        month: Int,
        celebrated: Boolean
    ): Flow<List<Birthday>>

    @Query("UPDATE birthdays SET birthday_celebrated = 0")
    suspend fun resetCelebratedBirthdays()

    @Delete
    suspend fun delete(vararg birthday: Birthday)
}