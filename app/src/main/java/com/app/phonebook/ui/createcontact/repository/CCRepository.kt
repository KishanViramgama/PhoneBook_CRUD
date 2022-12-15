package com.app.phonebook.ui.createcontact.repository

import com.app.phonebook.database.DatabaseClient
import com.app.phonebook.ui.home.item.PhoneBook
import javax.inject.Inject

class CCRepository @Inject constructor(private val databaseClient: DatabaseClient) {

    suspend fun getSingleUserContact(id: String): PhoneBook {
        return databaseClient.appDatabase.userTask()!!.getSingleContact(id)
    }

    suspend fun insertUserContact(phoneBook: PhoneBook): Int {
        return databaseClient.appDatabase.userTask()?.insertData(phoneBook)!!.toInt()
    }

    suspend fun updateUserContact(phoneBook: PhoneBook): Int {
        return databaseClient.appDatabase.userTask()?.updateUserContact(phoneBook)!!
    }

}