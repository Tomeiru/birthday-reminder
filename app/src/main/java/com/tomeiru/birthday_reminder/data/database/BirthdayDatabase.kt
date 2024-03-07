package com.tomeiru.birthday_reminder.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tomeiru.birthday_reminder.data.database.birthday.Birthday
import com.tomeiru.birthday_reminder.data.database.birthday.BirthdayDao


@Database(entities = [Birthday::class], version = 1)
abstract class BirthdayDatabase : RoomDatabase() {
    abstract fun birthdayDao(): BirthdayDao

    companion object {
        private const val db_name = "birthday.db"
        private lateinit var database: BirthdayDatabase

        fun getInstance(context: Context): BirthdayDatabase {
            if (!this::database.isInitialized) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    BirthdayDatabase::class.java,
                    db_name
                ).fallbackToDestructiveMigration().build()
            }
            return database
        }
    }
}