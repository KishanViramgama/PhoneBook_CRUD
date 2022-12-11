package com.app.phonebook.ui.contactdetail.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.phonebook.R
import com.app.phonebook.theme.PhoneBookTheme

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
                        TopAppBar(title = { Text(text = resources.getString(R.string.home)) },
                            Modifier.background(color = Color.Magenta),
                            actions = {
                                IconButton(onClick = {

                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outline_edit),
                                        contentDescription = resources.getString(R.string.app_name),
                                        modifier = Modifier
                                            .height(24.dp)
                                            .width(24.dp)
                                    )
                                }
                                IconButton(onClick = { /*TODO*/ }) {
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
                            text = "Kishan Viramgama",
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
                    .weight(1f),
                painter = painterResource(R.drawable.ic_outline_call),
                title = resources.getString(R.string.call)
            )
            common(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .weight(1f),
                painter = painterResource(R.drawable.ic_outline_sms),
                title = resources.getString(R.string.sms)
            )
            common(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp)
                    .weight(1f),
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
                text = title, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 10.dp)
            )
        }
    }

}