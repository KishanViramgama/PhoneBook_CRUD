package com.app.phonebook.ui.createcontact.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.phonebook.R
import com.app.phonebook.ui.createcontact.repository.CCRepository
import com.app.phonebook.ui.home.item.PhoneBook
import com.app.phonebook.util.Method
import com.app.phonebook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CCViewModel @Inject constructor(
    private val context: Context, private val ccRepository: CCRepository
) : ViewModel() {

    private val insertContactLiveData: MutableLiveData<Resource<PhoneBook>> = MutableLiveData()
    val insertContactObservable: LiveData<Resource<PhoneBook>> = insertContactLiveData

    private val userContactUpdateLiveData: MutableLiveData<Resource<PhoneBook>> = MutableLiveData()
    val userContactUpdateObservable: LiveData<Resource<PhoneBook>> = userContactUpdateLiveData

    private val singleContactLiveData: MutableLiveData<Resource<PhoneBook>> = MutableLiveData()
    val getSingleContact: LiveData<Resource<PhoneBook>> = singleContactLiveData

    fun insertData(phoneBook: PhoneBook) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val id: Int = ccRepository.insertUserContact(phoneBook)
                if (id > 0) {
                    insertContactLiveData.postValue(
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
                    insertContactLiveData.postValue(
                        Resource.error(
                            context.getString(R.string.contactNotCreate), null
                        )
                    )
                }
            } catch (e: Exception) {
                insertContactLiveData.postValue(
                    Resource.error(
                        context.resources.getString(R.string.error), null
                    )
                )
            }
        }
    }

    fun updateUserContact(phoneBook: PhoneBook){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val id: Int = ccRepository.updateUserContact(phoneBook)
                if (id > 0) {
                    userContactUpdateLiveData.postValue(
                        Resource.success(
                            phoneBook
                        )
                    )
                } else {
                    userContactUpdateLiveData.postValue(
                        Resource.error(
                            context.getString(R.string.contactNotCreate), null
                        )
                    )
                }
            } catch (e: Exception) {
                userContactUpdateLiveData.postValue(
                    Resource.error(
                        context.resources.getString(R.string.error), null
                    )
                )
            }
        }
    }

    fun getSingleContact(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                singleContactLiveData.postValue(
                    Resource.success(
                        ccRepository.getSingleUserContact(
                            id
                        )
                    )
                )
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