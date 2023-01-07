package com.app.phonebook.ui.contactdetail.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.phonebook.R
import com.app.phonebook.ui.contactdetail.repository.CDRepository
import com.app.phonebook.ui.home.item.PhoneBook
import com.app.phonebook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CDViewModel @Inject constructor(
    private val context: Context,
    private val cdRepository: CDRepository
) : ViewModel() {

    private val singleContactMutableLiveData: MutableLiveData<Resource<PhoneBook>> =
        MutableLiveData()
    val singleContactLiveData: LiveData<Resource<PhoneBook>> = singleContactMutableLiveData

    private val deleteContactMutableLiveData: MutableLiveData<Resource<String>> = MutableLiveData()
    val deleteSingleContactLiveData: LiveData<Resource<String>> = deleteContactMutableLiveData

    fun callPhone(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, context.resources.getString(R.string.wrong), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun sms() {
        try {
            val defaultSmsPackageName =
                Telephony.Sms.getDefaultSmsPackage(context)
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "text/plain"
            sendIntent.setPackage(defaultSmsPackageName)
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(sendIntent)
        } catch (e: Exception) {
            Toast.makeText(context, context.resources.getString(R.string.wrong), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun getSingleContact(id: String) {
        singleContactMutableLiveData.value = Resource.loading(null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                singleContactMutableLiveData.postValue(
                    Resource.success(
                        cdRepository.getSingleUserContact(
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

    fun deleteContact(id: String) {
        deleteContactMutableLiveData.value = Resource.loading(null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val id: Int = cdRepository.deleteUserContact(id)
                if (id > 0) {
                    deleteContactMutableLiveData.postValue(Resource.success(id.toString()))
                } else {
                    deleteContactMutableLiveData.postValue(
                        Resource.error(
                            context.resources.getString(
                                R.string.dataNoDelete
                            ), null
                        )
                    )
                }
            } catch (e: Exception) {
                deleteContactMutableLiveData.postValue(
                    Resource.error(
                        context.resources.getString(R.string.wrong),
                        null
                    )
                )
            }
        }
    }

}