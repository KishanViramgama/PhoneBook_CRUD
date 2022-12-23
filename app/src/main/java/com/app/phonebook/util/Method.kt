package com.app.phonebook.util

import android.content.Context
import android.util.Patterns
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.app.phonebook.R
import javax.inject.Inject

class Method @Inject constructor(private val context: Context) {

    fun isValidMail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    @Composable
    fun ShowMyDialog(yes: () -> Unit, no: () -> Unit, title: String, msg: String) {
        AlertDialog(onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
            title = {
                Text(title)
            },
            text = {
                Text(msg)
            },
            confirmButton = {
                TextButton(
                    onClick = yes
                ) {
                    Text(context.resources.getString(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = no
                ) {
                    Text(context.resources.getString(R.string.no))
                }
            })
    }

}