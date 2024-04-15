package com.example.eventapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventapp.ui.theme.EventAppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var dummyUser = User(
            id = 1L,
            name = "John",
            surname = "Doe",
            password = "password123",
            age = 30,
            image_id = 1, // Replace with your actual default image resource ID
            description = "This is a dummy user",
            nickname = "johndoe"
        )



        super.onCreate(savedInstanceState)
        setContent {
            EventAppTheme {



                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    var isLoggedIn by rememberSaveable { mutableStateOf(false) }

                    if(isLoggedIn) {
                            MainScreen()

                    } else {
                        LoginScreen(authService = ServiceLocator.authService, user = dummyUser){a->
                            isLoggedIn = a

                        }
                    }

                }
            }
        }
    }
}


@Composable
fun MainScreen() {

    var route by remember { mutableStateOf("Home") }
    var navController = rememberNavController()

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->

        NavHost(navController = navController, startDestination = "Home") {

            composable("Home") {
                HomeScreen(innerPadding = innerPadding, navController = navController)
            }

            composable("Profile") {
                ProfileScreen()
            }

            composable("Creator") {
                CreatorScreen(innerPadding = innerPadding, navController = navController)
            }
        }


    }


}


@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navController: NavController
) {



    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {




        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {





            var selectedDate by remember {
                mutableStateOf(LocalDate.now())
            }

            val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")


            TextButton(
                onClick = { selectedDate=selectedDate.minusDays(1) },
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(Alignment.Center),
            ) {
                Text(
                    text = "<",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold


                )

            }





            TextButton(
                onClick = { /*TODO*/     },
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(Alignment.Center),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),


                ) {
                Text(
                    text = selectedDate.format(formatter),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            TextButton(
                onClick = { selectedDate = selectedDate.plusDays(1) },
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(Alignment.Center),
            ) {
                Text(
                    text = ">",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold

                )

            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        ) {}

        Column(Modifier.verticalScroll(rememberScrollState())) {


            repeat(5) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),


                    ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {


                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Biljar (14:30)",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                text = "Ivan Lazarušić",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Icon(
                            painter = painterResource(id = R.drawable.eight_ball_icon),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterVertically)
                                .fillMaxWidth(.25f)

                        )


                    }
                }
            }
        }


    }

}


@Composable
fun DateSelector(selectedDate: LocalDate, formatter: DateTimeFormatter, onDateChange: (Long) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { onDateChange(-1L) }) {
            Text("<", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        }
        TextButton(onClick = { /* Display DatePicker or similar action */ }) {
            Text(selectedDate.format(formatter), style = MaterialTheme.typography.titleMedium, color = Color.White)
        }
        TextButton(onClick = { onDateChange(1L) }) {
            Text(">", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun EventCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(event.eventName, style = MaterialTheme.typography.headlineMedium)
                Text(event.eventType, style = MaterialTheme.typography.bodyMedium)
            }
            Icon(
                painter = painterResource(id = R.drawable.eight_ball_icon),
                contentDescription = "",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(.25f)
            )
        }
    }
}





//preview for HomeScreen


