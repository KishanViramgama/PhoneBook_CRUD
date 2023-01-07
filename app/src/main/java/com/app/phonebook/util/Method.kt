package com.app.phonebook.util

import android.content.Context
import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.app.phonebook.R
import javax.inject.Inject

class Method @Inject constructor(private val context: Context) {

    @Composable
    fun ShowLoader(isShow: Boolean = false) {
        if (isShow) {
            Dialog(
                onDismissRequest = { },
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                ) {
                    Column {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 20.dp)
                        )
                        Text(
                            text = context.resources.getString(R.string.loading),
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(
                                top = 20.dp,
                                bottom = 20.dp,
                                start = 20.dp,
                                end = 20.dp
                            )
                        )
                    }

                }

            }
        }
    }

    @Composable
    fun ShowMyDialog(yes: () -> Unit, no: () -> Unit, title: String, msg: String) {
        AlertDialog(onDismissRequest = {}, title = {
            Text(title)
        }, text = {
            Text(msg)
        }, confirmButton = {
            TextButton(
                onClick = yes
            ) {
                Text(context.resources.getString(R.string.yes))
            }
        }, dismissButton = {
            TextButton(
                onClick = no
            ) {
                Text(context.resources.getString(R.string.no))
            }
        })
    }

}