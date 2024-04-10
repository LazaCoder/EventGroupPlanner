package com.example.eventapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eventapp.ui.theme.EventAppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()


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

        NavHost(navController = navController , startDestination = "Home" ){

            composable("Home"){
                HomeScreen(innerPadding = innerPadding, navController = navController)
            }

            composable("Profile"){
                ProfileScreen(innerPadding = innerPadding, navController = navController)
            }

            composable("Creator"){
                CreatorScreen(innerPadding = innerPadding, navController = navController)
            }
        }



    }


}



@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navController: NavController
){


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            TextButton(
                onClick = { /*TODO*/ },
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

            var current = LocalDate.now()
            var formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
            var formatted = current.format(formatter)


            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentSize(Alignment.Center),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),


                ) {
                Text(
                    text = formatted,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }

            TextButton(
                onClick = { /*TODO*/ },
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

//preview for HomeScreen
@Preview
@Composable
fun HomeScreenPreview() {
    MainScreen()
}

