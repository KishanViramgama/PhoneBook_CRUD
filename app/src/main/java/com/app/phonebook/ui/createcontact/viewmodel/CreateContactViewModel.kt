package com.app.phonebook.ui.createcontact.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.phonebook.R
import com.app.phonebook.database.DatabaseClient
import com.app.phonebook.ui.createcontact.item.PhoneBook
import com.app.phonebook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateContactViewModel @Inject constructor(
    private val context: Context,
    private val databaseClient: DatabaseClient
) :
    ViewModel() {

    private val freeDataSend: MutableLiveData<Resource<MutableList<PhoneBook>>> = MutableLiveData()
    val freeDataSendObservable: LiveData<Resource<MutableList<PhoneBook>>> = freeDataSend

    fun getData() {
        freeDataSend.value = Resource.loading(null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                freeDataSend.postValue(Resource.success(databaseClient.appDatabase.userTask()?.getData()))
            } catch (e: Exception) {
                freeDataSend.postValue(Resource.error(context.resources.getString(R.string.error), null))
            }
        }
    }

}