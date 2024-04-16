package com.example.eventapp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController



fun getUserFromSharedPreferences(context: Context): User? {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val name = sharedPreferences.getString("name", null)
    val surname = sharedPreferences.getString("surname", null)
    val age = sharedPreferences.getInt("age", -1)
    val imageId =  sharedPreferences.getString("imageId", "laza_profilna") // Assuming a default drawable
    val description = sharedPreferences.getString("description", "")
    val nickname = sharedPreferences.getString("nickname", null)
    val id = sharedPreferences.getLong("id", -1)



    Log.d("1.getUserFromSharedPreferences", "User: $name $surname $age $imageId $description $nickname  $id")


    if (name == null || surname == null || age == -1 ) return null // Basic validation


    Log.d("2.getUserFromSharedPreferences", "User: $name $surname $age $imageId $description $nickname  $id")

    return imageId?.let {
        User(
        id = id,
        name = name,
        surname = surname,
        password = "123",
        age = age,
        image_id = it,
        description = description ?: "",
        nickname = nickname
    )
    }
}



@SuppressLint("DiscouragedApi")
@Composable
fun ProfileScreen() {

    val user = getUserFromSharedPreferences(LocalContext.current)




    Log.d("ProfileScreen", "User: $user")


    user?.let { currentUser ->
        ProfileContent(currentUser, PaddingValues(top = 100.dp))
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("No user data available", style = MaterialTheme.typography.bodyLarge)
    }
}



@SuppressLint("DiscouragedApi")
@Composable
fun ProfileContent(user: User, innerPadding: PaddingValues) {

    val realId = LocalContext.current.resources.getIdentifier(user?.image_id ?: "laza_profilna", "drawable", LocalContext.current.packageName)

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {

            Image(
                painter = painterResource(id = realId), contentDescription = "",

                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
                    .size(200.dp)
                    .clip(MaterialTheme.shapes.large),
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(text = user.surname, style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }

        }

        HorizontalDivider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)

        Spacer(modifier = Modifier.size(16.dp))


        Row (horizontalArrangement = Arrangement.Center, modifier =
        Modifier.fillMaxWidth(),
        ) {


            Card(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(start = 24.dp, end = 16.dp),
                shape = MaterialTheme.shapes.medium,

                ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .padding(bottom = 8.dp, top = 0.dp, start = 8.dp, end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Nadimak",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    )

                    if (user.nickname != null) {
                        Text(
                            text = user.nickname,
                            style = MaterialTheme.typography.headlineSmall,

                            )
                    }

                }

            }


            Card(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(start = 20.dp, end = 24.dp),
                shape = MaterialTheme.shapes.medium,

                ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .padding(bottom = 8.dp, top = 0.dp, start = 8.dp, end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Godine",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    )

                    Text(
                        text = user.age.toString(),
                        style = MaterialTheme.typography.headlineSmall,

                        )

                }

            }


        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "O meni",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = user.description,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

            }

        }


    }

}


