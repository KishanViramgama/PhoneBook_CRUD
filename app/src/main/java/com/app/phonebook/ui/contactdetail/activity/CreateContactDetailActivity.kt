package com.app.phonebook.ui.contactdetail.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.cardview.widget.CardView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.phonebook.R
import com.app.phonebook.ui.theme.PhoneBookTheme

@OptIn(ExperimentalMaterial3Api::class)
class CreateContactDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PhoneBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        TopAppBar(
                            title = { Text(text = resources.getString(R.string.home)) },
                        )
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
                            text = "Kishan Viramgama",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
                                .fillMaxWidth()
                        )
                        Divider(modifier = Modifier.padding(top = 20.dp))
                        Row(Modifier.height(100.dp)) {

                        }
                        Divider(modifier = Modifier.padding(top = 20.dp))
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(
                                    top = 25.dp,
                                    start = 20.dp,
                                    end = 20.dp,
                                    bottom = 20.dp
                                )
                        ) {
                            Column(

                            ) {
                                Text(
                                    text = "This is a card view",
                                )
                            }
                        }
                    }

                }
            }
        }

    }
}