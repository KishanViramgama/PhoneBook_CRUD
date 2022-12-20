package com.app.phonebook.ui.contactdetail.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.app.phonebook.R
import com.app.phonebook.theme.PhoneBookTheme
import com.app.phonebook.ui.contactdetail.viewmodel.CDViewModel
import com.app.phonebook.ui.createcontact.activity.CreateContactActivity
import com.app.phonebook.ui.home.item.PhoneBook
import com.app.phonebook.util.LiveDataType
import com.app.phonebook.util.Method
import com.app.phonebook.util.Status
import com.app.phonebook.util.Type
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class ContactDetailActivity : ComponentActivity() {

    private lateinit var id: String
    private var position: Int = 0
    private var name by mutableStateOf("")
    private lateinit var ccdViewModel: CDViewModel

    @Inject
    lateinit var method: Method

    @Inject
    lateinit var mutableLiveData: MutableLiveData<LiveDataType<PhoneBook>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        id = intent.getIntExtra("id", 0).toString()
        position = intent.getIntExtra("position", 0)

        ccdViewModel = ViewModelProvider(this)[CDViewModel::class.java]
        ccdViewModel.getSingleContact(id)
        ccdViewModel.getSingleContact.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    name = it.data?.name.toString()
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                    Toast.makeText(
                        this, it.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        setContent {
            PhoneBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        TopAppBar(title = { Text(text = resources.getString(R.string.home)) },
                            Modifier.background(color = Color.Magenta),
                            actions = {
                                IconButton(onClick = {
                                    startActivity(
                                        Intent(
                                            this@ContactDetailActivity,
                                            CreateContactActivity::class.java
                                        ).putExtra("type", "edit").putExtra("id", id)
                                            .putExtra("position", position)
                                    )
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outline_edit),
                                        contentDescription = resources.getString(R.string.app_name),
                                        modifier = Modifier
                                            .height(24.dp)
                                            .width(24.dp)
                                    )
                                }
                                IconButton(onClick = {
                                    mutableLiveData.value =
                                        LiveDataType.callObserver(null, position, Type.DELETE)
                                    finish()
                                    //method.Dialog()
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outline_delete),
                                        contentDescription = resources.getString(R.string.app_name),
                                        modifier = Modifier
                                            .height(24.dp)
                                            .width(24.dp)
                                    )
                                }
                            })
                        Box(
                            modifier = Modifier.align(CenterHorizontally),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(120.dp), onDraw = {
                                val size = 120.dp.toPx()
                                drawCircle(
                                    color = Color.Magenta, radius = size / 2f
                                )
                            })
                            Text(
                                text = "K", textAlign = TextAlign.Center
                            )
                        }
                        Text(
                            text = name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
                                .fillMaxWidth()
                        )
                        Divider(modifier = Modifier.padding(top = 20.dp))
                        View()
                        Divider()
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(
                                    top = 25.dp, start = 20.dp, end = 20.dp, bottom = 20.dp
                                )
                        ) {
                            Column() {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = "This is a card view",
                                )
                            }
                        }
                    }

                }
            }
        }

    }

    @Composable
    @Preview
    fun View() {
        Row {
            common(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .weight(1f)
                    .clickable {
                        ccdViewModel.callPhone("")
                    },
                painter = painterResource(R.drawable.ic_outline_call),
                title = resources.getString(R.string.call)
            )
            common(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .weight(1f)
                    .clickable { },
                painter = painterResource(R.drawable.ic_outline_sms),
                title = resources.getString(R.string.sms)
            )
            common(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .weight(1f)
                    .clickable { },
                painter = painterResource(R.drawable.ic_outline_videocam),
                title = resources.getString(R.string.video)
            )
        }
    }

    @Composable
    fun common(modifier: Modifier, painter: Painter, title: String) {
        Column(
            modifier = modifier,
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painter,
                contentDescription = title,
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            )
            Text(
                text = title,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }

}