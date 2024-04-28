package com.example.eventapp


import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun LoginScreen(authService: AuthService, user: User, onLogin: (Boolean) -> Unit) {

    val context = LocalContext.current
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var loginError by remember { mutableStateOf("") }


    fun saveUserToSharedPreferences(user: User, context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", user.name)
        editor.putString("surname", user.surname)
        editor.putInt("age", user.age)
        editor.putString("imageId", user.image_id)
        editor.putString("description", user.description)
        editor.putString("nickname", user.nickname)
        editor.apply()
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.Top,
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


                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.main_icon_transparent),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)

                    )


                    OutlinedTextField(
                        value = name, onValueChange = { input -> name = input },
                        label = {
                            Text("Name")

                        },

                        )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { input -> password = input },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),


                        )

                    TextButton(
                        onClick = {
                            GlobalScope.launch {
                                CoroutineScope(Dispatchers.IO).launch {
                                    val response = authService.login(LoginRequest(name, password))
                                    withContext(Dispatchers.Main) {
                                        if (response.isSuccessful) {
                                            onLogin(true)
                                            saveUserToSharedPreferences(
                                                response.body()!!,
                                                context = context
                                            )


                                        } else {
                                            loginError =
                                                response.errorBody()?.string() ?: "Unknown error"
                                            onLogin(false)
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(24.dp).width(120.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                        shape = MaterialTheme.shapes.medium.copy(
                            CornerSize(25)
                        )
                    ) {
                        Text("Login")

                    }
                    if (loginError.isNotEmpty()) {
                        Text(loginError, color = Color.Red)
                    } else {

                        Text(loginError, color = Color.Red)

                    }


                }

            }


        }


    }


}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        authService = ServiceLocator.authService,
        user = User(1, "John", "Doe", "password", 25, "image_id", "description", "nickname")
    ) { }
}








