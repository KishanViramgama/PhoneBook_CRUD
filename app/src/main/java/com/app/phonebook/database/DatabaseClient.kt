package com.app.phonebook.database

import android.content.Context
import androidx.room.Room

class DatabaseClient(context: Context) {

    val appDatabase: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "PhoneBook")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

}