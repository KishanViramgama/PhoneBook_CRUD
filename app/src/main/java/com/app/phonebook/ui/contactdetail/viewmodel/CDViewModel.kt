package com.app.phonebook.ui.contactdetail.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
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
class CDViewModel @Inject constructor(private val context: Context) : ViewModel() {

    @Inject
    lateinit var database: DatabaseClient

    private val singleContactLiveData: MutableLiveData<Resource<PhoneBook>> = MutableLiveData()
    val getSingleContact: LiveData<Resource<PhoneBook>> = singleContactLiveData

    fun callPhone(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(intent)
    }

    fun getSingleContact(id: String) {
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