package com.app.phonebook.ui.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.phonebook.R
import com.app.phonebook.database.DatabaseClient
import com.app.phonebook.ui.home.item.PhoneBook
import com.app.phonebook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Context,
    private val databaseClient: DatabaseClient
) :
    ViewModel() {

    private val liveDataPhoneBook: MutableLiveData<Resource<MutableList<PhoneBook>>> =
        MutableLiveData()
    val getDataPhoneBook: LiveData<Resource<MutableList<PhoneBook>>> = liveDataPhoneBook

    fun getData() {
        liveDataPhoneBook.value = Resource.loading(null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                liveDataPhoneBook.postValue(
                    Resource.success(
                        databaseClient.appDatabase.userTask()?.getAllContact()
                    )
                )
            } catch (e: Exception) {
                liveDataPhoneBook.postValue(
                    Resource.error(
                        context.resources.getString(R.string.error),
                        null
                    )
                )
            }
        }
    }

}