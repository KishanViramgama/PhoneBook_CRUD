package com.app.phonebook.ui.home.repository

import com.app.phonebook.database.DatabaseClient
import com.app.phonebook.ui.home.item.PhoneBook
import javax.inject.Inject

class HomeRepository @Inject constructor(private val databaseClient: DatabaseClient) {

    suspend fun getUserContact(): MutableList<PhoneBook> {
        return databaseClient.appDatabase.userTask()?.getAllContact()!!
    }

}