package com.app.phonebook.ui.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.phonebook.R
import com.app.phonebook.ui.home.item.PhoneBook
import com.app.phonebook.ui.home.repository.HomeRepository
import com.app.phonebook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Context, private val homeRepository: HomeRepository
) : ViewModel() {

    private val phoneBookMutableLiveData: MutableLiveData<Resource<MutableList<PhoneBook>>> =
        MutableLiveData()
    val phoneBookLiveData: LiveData<Resource<MutableList<PhoneBook>>> = phoneBookMutableLiveData

    fun getData() {
        phoneBookMutableLiveData.value = Resource.loading(null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                phoneBookMutableLiveData.postValue(
                    Resource.success(
                        homeRepository.getUserContact()
                    )
                )
            } catch (e: Exception) {
                phoneBookMutableLiveData.postValue(
                    Resource.error(
                        context.resources.getString(R.string.wrong), null
                    )
                )
            }
        }
    }

}