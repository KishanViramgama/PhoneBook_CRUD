package com.app.phonebook.ui.createcontact.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.phonebook.database.DatabaseClient
import com.app.phonebook.ui.home.item.PhoneBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateContactViewModel @Inject constructor(
) : ViewModel() {

    @Inject
    lateinit var database: DatabaseClient

    fun insertData(phoneBook: PhoneBook) {
        CoroutineScope(Dispatchers.IO).launch {
            val isTrue = database.appDatabase.userTask()?.insertData(phoneBook)
            Log.d("data_information", isTrue.toString())
        }
    }

}