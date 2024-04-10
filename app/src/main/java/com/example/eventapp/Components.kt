package com.example.eventapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun TopBar() {


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {


            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(1.0f)
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Vlakovi",
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(0.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.main_icon_transparent),
                        contentDescription = " ",
                        tint = Color.Black,

                        )


                    Text(
                        text = "Lete",
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(0.dp)
                    )
                }

            }


        }


    }
}

//create a BottomBar composable function that displays a Text composable
@Composable
fun BottomBar(navController: NavController) {

    var current = rememberSaveable{ mutableStateOf("Home") }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        color = MaterialTheme.colorScheme.primary
    ) {

        NavigationBar(Modifier.fillMaxWidth()) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
            ) {


                NavigationBarItem(

                    selected = current.value == "Home",
                    onClick = {
                        current.value = "Home"
                        navController.navigate("Home")

                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = " ",
                            tint = if (current.value == "Home") Color.Black else Color.White
                        )
                    },
                    label = {
                        Text("Home", color = Color.White)

                    })
                NavigationBarItem(
                    selected = current.value == "Creator",
                    onClick = {
                              current.value = "Creator"
                                navController.navigate("Creator")

                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = " ",
                            tint = if (current.value == "Creator") Color.Black else Color.White
                        )
                    },
                    label = {
                        Text("Event Creator", color = Color.White)
                    })
                NavigationBarItem(
                    selected = current.value == "Profile",
                    onClick = {
                              current.value = "Profile"
                              navController.navigate("Profile")


                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = " ",
                            tint = if (current.value == "Profile") Color.Black else Color.White
                        )
                    },
                    label = {
                        Text("Profile", color = Color.White)
                    })

            }

        }


    }
}


//create preview for TopBar
@Preview
@Composable
fun TopBarPreview() {
    TopBar()

}

//create preview for BottomBar
@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(navController = rememberNavController())
}

