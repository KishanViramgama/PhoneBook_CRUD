package com.app.phonebook.util

import android.util.Patterns
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

class Method {

    fun isValidMail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    @Composable
    fun Dialog() {
        AlertDialog(onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
            title = {
                Text(text = "Title")
            },
            text = {
                Text(
                    "This area typically contains the supportive text " +
                            "which presents the details regarding the Dialog's purpose."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                    }
                ) {
                    Text("Dismiss")
                }
            })
    }

}