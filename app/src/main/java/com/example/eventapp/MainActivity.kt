package com.example.eventapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

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




    val dummyUser = User(
        id = 1L,
        name = "John",
        surname = "Doe",
        password = "password123",
        age = 30,
        image_id = "laza_profilna", // Replace with your actual default image resource ID
        description = "This is a dummy user",
        nickname = "johndoe"
    )




    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            LoginScreen(authService = ServiceLocator.authService, user = dummyUser) { isLoggedIn ->
                if (isLoggedIn && navController.currentBackStackEntry?.destination?.route != "Home") {
                    navController.navigate("Home") {
                        popUpTo("Login") { inclusive = true }
                    }
                }
            }
        }

        composable("Home") {
            ScaffoldWithBars(navController) { innerPadding ->
                HomeScreen(innerPadding, navController)
            }
        }

        composable("Profile") {
            ScaffoldWithBars(navController) {
                ProfileScreen()
            }
        }

        composable("Creator") {
            ScaffoldWithBars(navController) { innerPadding ->
                CreatorScreen(innerPadding, navController)
            }
        }
    }
}

@Composable
fun ScaffoldWithBars(navController: NavController, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    viewModel: EventViewModel = viewModel()
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.")
    val events = viewModel.events.value // Directly access the value here.
    val isLoading = viewModel.isLoading.value
    val swipeThreshold = 200f // Define a threshold for swipe sensitivity
    var totalDragDistance by remember { mutableFloatStateOf(0f) }


    LaunchedEffect(selectedDate) {
        viewModel.loadEvents(selectedDate)
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(innerPadding)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    totalDragDistance += dragAmount
                    if (abs(totalDragDistance) > swipeThreshold) {
                        if (totalDragDistance > 0) {
                            selectedDate = selectedDate.minusDays(1)
                        } else {
                            selectedDate = selectedDate.plusDays(1)
                        }
                        totalDragDistance = 0f // Reset the accumulated drag distance
                    }
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        DateSelector(selectedDate, formatter) { delta ->
            selectedDate = selectedDate.plusDays(delta)
        }


        if (isLoading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {


            LazyColumn() {

                if (events.isEmpty()) {

                    item {

                        Text(
                            "No events for this date",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 50.dp)
                        )
                    }
                } else {

                    itemsIndexed(events) { index,event ->
                        AnimatedEventCard(event, index)
                    }

                }
            }

        }
    }
}


@Composable
fun AnimatedEventCard(event: Event, index: Int) {

    var isVisible by remember { mutableStateOf(false) }

    // Apply a staggered effect based on the index
    LaunchedEffect(key1 = index) {
        delay(100L * index) // Stagger the animation by 100ms per card
        isVisible = true
    }


    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 1000)) +
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 1000)) +
                slideInVertically(initialOffsetY = { it / 3 }, animationSpec = tween(durationMillis = 1000)),
        exit = fadeOut(animationSpec = tween(durationMillis = 1000))
    ) {
        EventCard(event)
    }
}

@Composable
fun DateSelector(
    selectedDate: LocalDate,
    formatter: DateTimeFormatter,
    onDateChange: (Long) -> Unit
) {




    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onDateChange(-1L) }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Next Day")
        }
        TextButton(onClick = { /* Display DatePicker or similar action */ },
        ) {
            Text(
                selectedDate.format(formatter),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        IconButton(onClick = { onDateChange(1L) }) {
            Icon(Icons.Filled.ArrowForward, contentDescription = "Next Day")
        }
    }
}

@Composable
fun EventCard(event: Event) {

    val cardElevation = CardDefaults.cardElevation(
        defaultElevation = 8.dp,
        pressedElevation = 12.dp,
        focusedElevation = 10.dp,
        hoveredElevation = 10.dp
    )


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = cardElevation
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(event.eventName, style = MaterialTheme.typography.headlineMedium)
                Text(event.eventType, style = MaterialTheme.typography.bodyMedium)
                Text(text = event.eventTime)
            }

            val iconId = when (event.eventType) {
                "Kof" -> R.drawable.coffee_icon
                "Biljar" -> R.drawable.eight_ball_icon
                "Mesina" -> R.drawable.meat_icon
                "Dioklecijan" -> R.drawable.liquor_icon
                "Kviz" -> R.drawable.kviz_icon
                else -> R.drawable.sun_icon
            }

            Box(modifier =Modifier, contentAlignment = Alignment.CenterStart){
            Icon(painter = painterResource(id = iconId), contentDescription ="" ,
                tint = Color.Black,
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp)
            )}


            // Icon logic based on the event type can be handled here
        }
    }
}

// preview for event
@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    EventCard(
        Event(
            eventId = 1,
            createdById = 1,
            eventName = "Event Name",
            eventDate = "2022-12-12",
            eventDescription = "Event Description",
            eventType = "Event Type",
            eventTime = "Event Time"
        )
    )
}



