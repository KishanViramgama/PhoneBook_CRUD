package com.app.phonebook.ui.createcontact.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.room.Database
import com.app.phonebook.R
import com.app.phonebook.database.DatabaseClient
import com.app.phonebook.ui.createcontact.item.PhoneBook
import com.app.phonebook.ui.theme.PhoneBookTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateContact : ComponentActivity() {

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

    @Inject
    lateinit var database: DatabaseClient

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhoneBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(Modifier.padding(start = 20.dp, end = 20.dp)) {
                        TopAppBar(
                            title = { Text(text = resources.getString(R.string.create_contact)) },
                        )
                        editText(
                            name,
                            "Name",
                            "Please enter name",
                            nameErrorState,
                            nameFocusRequester,
                            KeyboardOptions(keyboardType = KeyboardType.Text),
                            onTextChanged = { name = it })
                        editText(
                            surname,
                            "Sure name",
                            "Please enter sure name",
                            surnameErrorState,
                            surnameFocusRequester,
                            KeyboardOptions(keyboardType = KeyboardType.Text),
                            onTextChanged = { surname = it })
                        editText(
                            company,
                            "Company",
                            "Please enter company",
                            companyErrorState,
                            companyFocusRequester,
                            KeyboardOptions(keyboardType = KeyboardType.Text),
                            onTextChanged = { company = it })
                        editText(
                            email,
                            "Email",
                            "Please enter email",
                            emailErrorState,
                            emailFocusRequester,
                            KeyboardOptions(keyboardType = KeyboardType.Email),
                            onTextChanged = { email = it })
                        editText(
                            phone,
                            "Phone no",
                            "Please enter phone no",
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
                                } else if (phone == "") {
                                    phoneErrorState = true
                                } else {
                                    database.appDatabase.userTask()?.insert(PhoneBook(null,name, surname, company, email, phone))
                                    Toast.makeText(
                                        this@CreateContact,
                                        "Data submit success fully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            },
                            Modifier
                                .padding(top = 20.dp)
                                .align(Alignment.CenterHorizontally),
                        ) {
                            Text(
                                text = "Submit"
                            )
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

        var name by remember { mutableStateOf(textName) }

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