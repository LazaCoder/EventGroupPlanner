package com.example.eventapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {

    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {


            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),


            ) {


                
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                    ) {

                    Icon(painter = painterResource(id = R.drawable.main_icon_transparent), contentDescription ="",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.fillMaxWidth().height(300.dp)

                    )


                    TextField(
                        value = "", onValueChange = { input -> name = input },
                        label = {
                            Text("Name")

                        },

                        )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = password,
                        onValueChange = { input -> password = input },
                        label = { Text("Password") })

                    TextButton(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                                shape = MaterialTheme.shapes.small.copy(
                                    CornerSize(25))
                        ) {
                        Text("Login")

                    }

                }
            }


        }


    }


}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}