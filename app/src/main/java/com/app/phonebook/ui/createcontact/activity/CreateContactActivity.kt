package com.app.phonebook.ui.createcontact.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.app.phonebook.R
import com.app.phonebook.theme.PhoneBookTheme
import com.app.phonebook.ui.createcontact.viewmodel.CCViewModel
import com.app.phonebook.ui.home.item.PhoneBook
import com.app.phonebook.util.*
import com.app.phonebook.util.Base.maxPhoneNumber
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CreateContactActivity : ComponentActivity() {

    private lateinit var type: String
    private var position: Int = 0
    private lateinit var id: String
    private lateinit var createContactViewModel: CCViewModel

    @Inject
    lateinit var method: Method

    @Inject
    lateinit var mutableLiveData: MutableLiveData<LiveDataType<PhoneBook>>

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
    private var validEmailErrorState = true
    private val emailFocusRequester = FocusRequester()

    //Phone no
    private var phone by mutableStateOf("")
    private var phoneErrorState by mutableStateOf(false)
    private val phoneFocusRequester = FocusRequester()

    //Show loading
    private var isShowLoading by mutableStateOf(false)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createContactViewModel = ViewModelProvider(this)[CCViewModel::class.java]

        type = intent.getStringExtra("type").toString()
        position = intent.getIntExtra("position", 0)
        if (type == "edit") {
            id = intent.getStringExtra("id").toString()
            createContactViewModel.getSingleContact(id)
        }

        createContactViewModel.insertContactLiveData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    isShowLoading = false
                    mutableLiveData.value =
                        LiveDataType.callObserver(it.data, position, Type.INSERT)
                    finish()
                    Toast.makeText(
                        this@CreateContactActivity,
                        resources.getString(R.string.createContactSuccess),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Status.LOADING -> {
                    isShowLoading = true
                }
                Status.ERROR -> {
                    isShowLoading = false
                    Toast.makeText(
                        this, it.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        createContactViewModel.userContactUpdateLiveData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    mutableLiveData.value =
                        LiveDataType.callObserver(it.data, position, Type.UPDATE)
                    finish()
                    Toast.makeText(
                        this@CreateContactActivity,
                        resources.getString(R.string.updateContactSuccess),
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

        createContactViewModel.singleContactLiveData.observe(this) {
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
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        TopAppBar(
                            title = { Text(text = title()) },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = resources.getString(R.string.app_name)
                                    )
                                }
                            },
                        )
                        editText(name,
                            resources.getString(R.string.name),
                            resources.getString(R.string.please_enter_name),
                            nameErrorState,
                            nameFocusRequester,
                            KeyboardOptions(
                                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                            ),
                            onTextChanged = { name = it })
                        editText(surname,
                            resources.getString(R.string.sureName),
                            resources.getString(R.string.please_enter_sureName),
                            surnameErrorState,
                            surnameFocusRequester,
                            KeyboardOptions(
                                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                            ),
                            onTextChanged = { surname = it })
                        editText(company,
                            resources.getString(R.string.company),
                            resources.getString(R.string.please_enter_company),
                            companyErrorState,
                            companyFocusRequester,
                            KeyboardOptions(
                                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                            ),
                            onTextChanged = { company = it })
                        editText(email,
                            resources.getString(R.string.email),
                            if (validEmailErrorState) resources.getString(R.string.please_enter_email) else resources.getString(
                                R.string.please_enter_valid_email
                            ),
                            emailErrorState,
                            emailFocusRequester,
                            KeyboardOptions(
                                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                            ),
                            onTextChanged = { email = it })
                        editText(
                            phone,
                            resources.getString(R.string.phoneNo),
                            resources.getString(R.string.please_enter_phoneNo),
                            phoneErrorState,
                            phoneFocusRequester,
                            KeyboardOptions(
                                keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done
                            ),
                            onTextChanged = { phone = it },
                            maxPhoneNumber
                        )
                        ElevatedButton(
                            onClick = {
                                nameErrorState = false
                                surnameErrorState = false
                                companyErrorState = false
                                emailErrorState = false
                                phoneErrorState = false
                                if (name.trim() == "") {
                                    nameErrorState = true
                                } else if (surname.trim() == "") {
                                    surnameErrorState = true
                                } else if (company.trim() == "") {
                                    companyErrorState = true
                                } else if (email.trim() == "") {
                                    validEmailErrorState = true
                                    emailErrorState = true
                                } else if (!isValidMail(email.trim())) {
                                    validEmailErrorState = false
                                    emailErrorState = true
                                } else if (phone.trim() == "") {
                                    phoneErrorState = true
                                } else {
                                    if (type == "create") {
                                        createContactViewModel.insertData(
                                            PhoneBook(
                                                null,
                                                name.trim(),
                                                surname.trim(),
                                                company.trim(),
                                                email.trim(),
                                                phone.trim()
                                            )
                                        )
                                    } else {
                                        createContactViewModel.updateUserContact(
                                            PhoneBook(
                                                id.toInt(),
                                                name.trim(),
                                                surname.trim(),
                                                company.trim(),
                                                email.trim(),
                                                phone.trim()
                                            )
                                        )
                                    }
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
                        method.ShowLoader(isShowLoading)
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
        keyboardOptions: KeyboardOptions,
        onTextChanged: (String) -> Unit,
        maxNumber: Int = 0
    ) {

        var name by mutableStateOf(textName)

        OutlinedTextField(
            value = name,
            onValueChange = {
                if (maxNumber == 0) {
                    name = it
                    onTextChanged(it)
                } else {
                    if (it.length <= maxNumber) {
                        name = it
                        onTextChanged(it)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester = focusRequester)
                .padding(top = 10.dp, start = 20.dp, end = 20.dp),
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
                modifier = Modifier.padding(top = 5.dp, start = 25.dp, end = 25.dp)
            )
        }
    }

    private fun title(): String {
        return if (type == "edit") {
            resources.getString(R.string.edit_contact)
        } else {
            resources.getString(R.string.create_contact)
        }
    }

}