package com.app.phonebook.ui.createcontact.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.app.phonebook.R
import com.app.phonebook.theme.PhoneBookTheme
import com.app.phonebook.ui.createcontact.viewmodel.CreateContactViewModel
import com.app.phonebook.ui.home.item.PhoneBook
import com.app.phonebook.util.Method
import com.app.phonebook.util.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CreateContactActivity : ComponentActivity() {

    private lateinit var type: String
    private lateinit var id: String
    private lateinit var createContactViewModel: CreateContactViewModel

    @Inject
    lateinit var method: Method

    @Inject
    lateinit var mutableLiveData: MutableLiveData<PhoneBook>

    //Name
    private var name by mutableStateOf("")
    private var nameErrorState by mutableStateOf(false)
    private val nameFocusRequester = FocusRequester()

    //Surname
    private var surname by mutableStateOf("")
    private var surnameErrorState by mutableStateOf(false)
    private val surnameFocusRequester = FocusRequester()

    //Company
    private var company by mutableStateOf("")
    private var companyErrorState by mutableStateOf(false)
    private val companyFocusRequester = FocusRequester()

    //Email
    private var email by mutableStateOf("")
    private var emailErrorState by mutableStateOf(false)
    private val emailFocusRequester = FocusRequester()

    //Phone no
    private var phone by mutableStateOf("")
    private var phoneErrorState by mutableStateOf(false)
    private val phoneFocusRequester = FocusRequester()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createContactViewModel = ViewModelProvider(this)[CreateContactViewModel::class.java]

        type = intent.getStringExtra("type").toString()
        if (type == "edit") {
            id = intent.getStringExtra("id").toString()
            createContactViewModel.getSingleContact(id)
        }

        createContactViewModel.phoneBookObservable.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    mutableLiveData.value = it.data
                    finish()
                    Toast.makeText(
                        this@CreateContactActivity,
                        resources.getString(R.string.createContactSuccess),
                        Toast.LENGTH_SHORT
                    ).show()
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

        createContactViewModel.getSingleContact.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    name = it.data?.name.toString()
                    surname = it.data?.surname.toString()
                    company = it.data?.company.toString()
                    email = it.data?.email.toString()
                    phone = it.data?.phone.toString()
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
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp)
                    ) {
                        TopAppBar(
                            title = { Text(text = resources.getString(R.string.create_contact)) },
                        )
                        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                            editText(name,
                                resources.getString(R.string.name),
                                resources.getString(R.string.please_enter_name),
                                nameErrorState,
                                nameFocusRequester,
                                KeyboardOptions(keyboardType = KeyboardType.Text),
                                onTextChanged = { name = it })
                            editText(surname,
                                resources.getString(R.string.sureName),
                                resources.getString(R.string.please_enter_sureName),
                                surnameErrorState,
                                surnameFocusRequester,
                                KeyboardOptions(keyboardType = KeyboardType.Text),
                                onTextChanged = { surname = it })
                            editText(company,
                                resources.getString(R.string.company),
                                resources.getString(R.string.please_enter_company),
                                companyErrorState,
                                companyFocusRequester,
                                KeyboardOptions(keyboardType = KeyboardType.Text),
                                onTextChanged = { company = it })
                            editText(email,
                                resources.getString(R.string.email),
                                resources.getString(R.string.please_enter_email),
                                emailErrorState,
                                emailFocusRequester,
                                KeyboardOptions(keyboardType = KeyboardType.Email),
                                onTextChanged = { email = it })
                            editText(phone,
                                resources.getString(R.string.phoneNo),
                                resources.getString(R.string.please_enter_phoneNo),
                                phoneErrorState,
                                phoneFocusRequester,
                                KeyboardOptions(keyboardType = KeyboardType.Phone),
                                onTextChanged = { phone = it })
                            ElevatedButton(
                                onClick = {
                                    nameErrorState = false
                                    surnameErrorState = false
                                    companyErrorState = false
                                    emailErrorState = false
                                    phoneErrorState = false
                                    if (name == "") {
                                        nameErrorState = true
                                    } else if (surname == "") {
                                        surnameErrorState = true
                                    } else if (company == "") {
                                        companyErrorState = true
                                    } else if (email == "") {
                                        emailErrorState = true
                                    } else if (!method.isValidMail(email)) {
                                        emailErrorState = true
                                    } else if (phone == "") {
                                        phoneErrorState = true
                                    } else {
                                        createContactViewModel.insertData(
                                            PhoneBook(
                                                null, name, surname, company, email, phone
                                            )
                                        )
                                    }

                                },
                                Modifier
                                    .padding(top = 20.dp, bottom = 40.dp)
                                    .align(Alignment.CenterHorizontally),
                            ) {
                                Text(
                                    text = resources.getString(R.string.submit)
                                )
                            }
                        }
                    }

                }
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun editText(
        textName: String,
        label: String,
        errorMSg: String,
        isError: Boolean,
        focusRequester: FocusRequester,
        keyboardOptions: KeyboardOptions = remember { KeyboardOptions.Default },
        onTextChanged: (String) -> Unit
    ) {

        var name by  mutableStateOf(textName)

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                onTextChanged(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester = focusRequester)
                .padding(top = 10.dp),
            label = {
                Text(label)
            },
            singleLine = true,
            isError = isError,
            keyboardOptions = keyboardOptions,
        )
        if (isError) {
            Text(
                text = errorMSg,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 5.dp, top = 5.dp)
            )
        }
    }

}