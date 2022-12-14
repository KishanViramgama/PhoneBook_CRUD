package com.app.phonebook.ui.createcontact.viewmodel

import android.content.Context
import android.util.Log
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
class CreateContactViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {

    @Inject
    lateinit var database: DatabaseClient

    private val phoneBookLiveData: MutableLiveData<Resource<PhoneBook>> = MutableLiveData()
    val phoneBookObservable: LiveData<Resource<PhoneBook>> = phoneBookLiveData

    private val singleContactLiveData: MutableLiveData<Resource<PhoneBook>> = MutableLiveData()
    val getSingleContact: LiveData<Resource<PhoneBook>> = singleContactLiveData

    fun insertData(phoneBook: PhoneBook) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val id: Int =
                    database.appDatabase.userTask()?.insertData(phoneBook)!!.toInt()
                if (id > 0) {
                    phoneBookLiveData.postValue(
                        Resource.success(
                            PhoneBook(
                                id,
                                phoneBook.name,
                                phoneBook.surname,
                                phoneBook.company,
                                phoneBook.email,
                                phoneBook.phone
                            )
                        )
                    )
                } else {
                    phoneBookLiveData.postValue(
                        Resource.error(
                            context.getString(R.string.contactNotCreate), null
                        )
                    )
                }
            } catch (e: Exception) {
                phoneBookLiveData.postValue(
                    Resource.error(
                        context.resources.getString(R.string.error), null
                    )
                )
            }
        }
    }

    fun getSingleContact(id:String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val phoneBook: PhoneBook = database.appDatabase.userTask()!!.getSingleContact(id)
                singleContactLiveData.postValue(Resource.success(phoneBook))
            } catch (e: Exception) {
                singleContactLiveData.postValue(
                    Resource.error(
                        context.resources.getString(R.string.error), null
                    )
                )
            }
        }
    }

}