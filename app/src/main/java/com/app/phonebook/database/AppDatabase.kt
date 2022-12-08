package com.app.phonebook.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.phonebook.ui.home.item.PhoneBook

@Database(entities = [PhoneBook::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userTask(): DataEntity?
}