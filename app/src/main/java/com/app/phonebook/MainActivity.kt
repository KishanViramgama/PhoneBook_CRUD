package com.app.phonebook

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.app.phonebook.ui.createcontact.activity.CreateContact
import com.app.phonebook.ui.createcontact.item.PhoneBook
import com.app.phonebook.ui.createcontact.viewmodel.CreateContactViewModel
import com.app.phonebook.ui.theme.PhoneBookTheme
import com.app.phonebook.util.Status
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("MutableCollectionMutableState")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    var phoneBook: MutableList<PhoneBook> by mutableStateOf(mutableListOf())
    var isShowProgress by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contactViewModel = ViewModelProvider(this)[CreateContactViewModel::class.java]
        contactViewModel.getData()
        contactViewModel.freeDataSendObservable.observe(this@MainActivity) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
                        phoneBook.addAll(it.data)
                        Toast.makeText(
                            this@MainActivity, phoneBook.size.toString(), Toast.LENGTH_SHORT
                        ).show()
                        Log.d("data_information", it.data.size.toString())
                    }
                    isShowProgress = false
                }
                Status.LOADING -> {
                    isShowProgress = true
                }
                Status.ERROR -> {
                    isShowProgress = false
                    Toast.makeText(
                        this@MainActivity, it.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        setContent {
            PhoneBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn() {
                        items(phoneBook.size) {
                            Card(
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
                                Text(
                                    text = phoneBook[it].name, modifier = Modifier.padding(
                                        start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp
                                    )
                                )
                            }
                        }
                    }
                    if (isShowProgress) {
                        Box(modifier = Modifier.size(width = 32.dp, height = 32.dp), Alignment.Center) {
                            CircularProgressIndicator(
                                strokeWidth = 4.dp
                            )
                        }
                    }
                    Box(contentAlignment = Alignment.BottomEnd) {
                        ExtendedFloatingActionButton(modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(end = 20.dp, bottom = 20.dp),
                            icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                            text = { Text(resources.getString(R.string.create_contact)) },
                            onClick = {
                                startActivity(
                                    Intent(
                                        this@MainActivity, CreateContact::class.java
                                    )
                                )
                            })
                    }
                }
            }
        }
    }
}