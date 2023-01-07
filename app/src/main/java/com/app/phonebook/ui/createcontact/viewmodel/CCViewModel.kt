package com.app.phonebook.ui.createcontact.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.phonebook.R
import com.app.phonebook.ui.createcontact.repository.CCRepository
import com.app.phonebook.ui.home.item.PhoneBook
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

    private val insertContactMutableLiveData: MutableLiveData<Resource<PhoneBook>> =
        MutableLiveData()
    val insertContactLiveData: LiveData<Resource<PhoneBook>> = insertContactMutableLiveData

    private val userContactUpdateMutableLiveData: MutableLiveData<Resource<PhoneBook>> =
        MutableLiveData()
    val userContactUpdateLiveData: LiveData<Resource<PhoneBook>> = userContactUpdateMutableLiveData

    private val singleContactMutableLiveData: MutableLiveData<Resource<PhoneBook>> =
        MutableLiveData()
    val singleContactLiveData: LiveData<Resource<PhoneBook>> = singleContactMutableLiveData

    fun insertData(phoneBook: PhoneBook) {
        insertContactMutableLiveData.value = Resource.loading(null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val id: Int = ccRepository.insertUserContact(phoneBook)
                if (id > 0) {
                    insertContactMutableLiveData.postValue(
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
                    insertContactMutableLiveData.postValue(
                        Resource.error(
                            context.getString(R.string.contactNotCreate), null
                        )
                    )
                }
            } catch (e: Exception) {
                insertContactMutableLiveData.postValue(
                    Resource.error(
                        context.resources.getString(R.string.wrong), null
                    )
                )
            }
        }
    }

    fun updateUserContact(phoneBook: PhoneBook) {
        userContactUpdateMutableLiveData.value = Resource.loading(null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val id: Int = ccRepository.updateUserContact(phoneBook)
                if (id > 0) {
                    userContactUpdateMutableLiveData.postValue(
                        Resource.success(
                            phoneBook
                        )
                    )
                } else {
                    userContactUpdateMutableLiveData.postValue(
                        Resource.error(
                            context.getString(R.string.contactNotCreate), null
                        )
                    )
                }
            } catch (e: Exception) {
                userContactUpdateMutableLiveData.postValue(
                    Resource.error(
                        context.resources.getString(R.string.wrong), null
                    )
                )
            }
        }
    }

    fun getSingleContact(id: String) {
        singleContactMutableLiveData.value = Resource.loading(null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                singleContactMutableLiveData.postValue(
                    Resource.success(
                        ccRepository.getSingleUserContact(
                            id
                        )
                    )
                )
            } catch (e: Exception) {
                singleContactMutableLiveData.postValue(
                    Resource.error(
                        context.resources.getString(R.string.wrong), null
                    )
                )
            }
        }
    }

}