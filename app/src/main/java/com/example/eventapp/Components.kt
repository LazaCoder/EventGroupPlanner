package com.example.eventapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


@Composable
fun TopBar(navController: NavController, toggleDrawer: () -> Unit) {


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp)

        ) {


            IconButton(onClick = toggleDrawer,
                modifier = Modifier.fillMaxHeight()
                    .width(90.dp)


            ) {

                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Hamburger",
                    modifier = Modifier
                        .width(48.dp)
                        .fillMaxHeight()
                    )

            }

               Text(text = navController.currentDestination?.route.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                   )


                    Icon(
                        painter = painterResource(id = R.drawable.main_icon_transparent),
                        contentDescription = " ",
                        tint = Color.Black,

                        )

        }


    }
}

//create a BottomBar composable function that displays a Text composable
@Composable
fun BottomBar(navController: NavController) {


    // Observes the current back stack entry to derive the current route
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    var current by rememberSaveable { mutableStateOf(currentRoute ?: "Home") }

    // Effect to update the current tab based on route changes
    LaunchedEffect(currentRoute) {
        current = currentRoute ?: "Home"
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        color = MaterialTheme.colorScheme.primary,
        tonalElevation = 4.dp  // Adds subtle elevation for depth
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            // Define tabs dynamically if possible or declare explicitly
            val tabs = listOf("Home", "Creator", "Profile")
            val icons = listOf(Icons.Default.Home, Icons.Default.Create, Icons.Default.AccountCircle, Icons.AutoMirrored.Filled.ExitToApp)
            val labels = listOf("Home", "Event Creator", "Profile")

            tabs.zip(icons.zip(labels)).forEach { (route, pair) ->
                val (icon, label) = pair
                NavigationBarItem(
                    selected = current == route,
                    onClick = {
                        if (current != route) {
                            current = route
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = if (current == route) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    alwaysShowLabel = false  // Set true to always show labels
                )
            }
        }
    }
}

//create preview for TopBar
@Preview
@Composable
fun TopBarPreview() {

    TopBar(navController = rememberNavController(), toggleDrawer = {})
}

//create preview for BottomBar
@Preview
@Composable
fun BottomBarPreview() {
    BottomBar(navController = rememberNavController())
}

