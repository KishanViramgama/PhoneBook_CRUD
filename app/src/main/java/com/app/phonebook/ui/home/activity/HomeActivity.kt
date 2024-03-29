package com.app.phonebook.ui.home.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.app.phonebook.R
import com.app.phonebook.theme.PhoneBookTheme
import com.app.phonebook.ui.contactdetail.activity.ContactDetailActivity
import com.app.phonebook.ui.createcontact.activity.CreateContactActivity
import com.app.phonebook.ui.home.item.PhoneBook
import com.app.phonebook.ui.home.viewmodel.HomeViewModel
import com.app.phonebook.util.LiveDataType
import com.app.phonebook.util.Status
import com.app.phonebook.util.Type
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComponentActivity() {

    var phoneBook = mutableStateListOf<PhoneBook>()
    private var isShowProgress by mutableStateOf(true)
    private var isShowNoDataFound by mutableStateOf(false)

    @Inject
    lateinit var liveData: LiveData<LiveDataType<PhoneBook>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        liveData.observe(this) {
            when (it.type) {
                Type.INSERT -> {
                    phoneBook.add(0, it.data!!)
                    isShowNoDataFound = phoneBook.size == 0
                }
                Type.UPDATE -> {
                    phoneBook.removeAt(it.position)
                    phoneBook.add(it.position, it.data!!)
                }
                Type.DELETE -> {
                    phoneBook.removeAt(it.position)
                    isShowNoDataFound = phoneBook.size == 0
                }
            }
        }

        val contactViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        contactViewModel.getData()
        contactViewModel.phoneBookLiveData.observe(this@HomeActivity) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
                        if (it.data.size != 0) {
                            isShowNoDataFound = false
                            phoneBook.addAll(it.data)
                        } else {
                            isShowNoDataFound = true
                        }
                    }
                    isShowProgress = false
                }
                Status.LOADING -> {
                    isShowProgress = true
                }
                Status.ERROR -> {
                    isShowProgress = false
                    Toast.makeText(
                        this@HomeActivity, it.message, Toast.LENGTH_SHORT
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
                    Column {
                        TopAppBar(
                            title = { Text(text = resources.getString(R.string.home)) },
                            colors = TopAppBarDefaults.topAppBarColors()
                        )
                        LazyColumn() {
                            items(phoneBook.size) {
                                Card(shape = RoundedCornerShape(4.dp),
                                    modifier = lazyColumnModifier(it).clickable {
                                        startActivity(
                                            Intent(
                                                this@HomeActivity, ContactDetailActivity::class.java
                                            ).putExtra("id", phoneBook[it].id)
                                                .putExtra("position", it)
                                        )
                                    }) {
                                    Row(
                                        modifier = Modifier.padding(
                                            start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp
                                        )
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Canvas(modifier = Modifier.size(50.dp), onDraw = {
                                                val size = 50.dp.toPx()
                                                drawCircle(
                                                    color = Color.Magenta, radius = size / 2f
                                                )
                                            })
                                            Text(
                                                text = phoneBook[it].name[0].toString(),
                                                modifier = Modifier.padding(
                                                    start = 10.dp, end = 10.dp
                                                ),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                        Text(
                                            text = phoneBook[it].name.toString(),
                                            modifier = Modifier
                                                .padding(
                                                    start = 10.dp, end = 10.dp
                                                )
                                                .align(Alignment.CenterVertically)
                                                .fillMaxWidth(),
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (isShowProgress) {
                        Box(
                            modifier = Modifier.size(width = 32.dp, height = 32.dp),
                            Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 4.dp
                            )
                        }
                    }
                    if (isShowNoDataFound) {
                        noDataFound()
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
                                        this@HomeActivity, CreateContactActivity::class.java
                                    ).putExtra("type", "create").putExtra("position", 0)
                                )
                            })
                    }
                }
            }
        }
    }

    private fun lazyColumnModifier(
        position: Int
    ): Modifier {
        return if (position < phoneBook.size - 1) {
            Modifier
                .padding(
                    start = 15.dp, end = 15.dp, top = 10.dp, bottom = 0.dp
                )
                .fillMaxWidth()
                .wrapContentHeight()
        } else {
            Modifier
                .padding(
                    start = 15.dp, end = 15.dp, top = 10.dp, bottom = 120.dp
                )
                .fillMaxWidth()
                .wrapContentHeight()
        }
    }

    @Composable
    private fun noDataFound() {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = resources.getString(R.string.noDataFound),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
        }
    }

}