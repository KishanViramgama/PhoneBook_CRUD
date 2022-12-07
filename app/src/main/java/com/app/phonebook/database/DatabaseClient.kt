package com.app.phonebook.database

import android.content.Context
import androidx.room.Room

class DatabaseClient(context: Context) {

    val appDatabase: AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "PhoneBook")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    /*companion object {
        @SuppressLint("StaticFieldLeak")
        private var mInstance: DatabaseClient? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance = DatabaseClient(context)
            }
            return mInstance
        }
    }*/

}