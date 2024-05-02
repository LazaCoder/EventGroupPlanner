package com.example.eventapp

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


fun getUserFromSharedPreferences(context: Context): User? {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val name = sharedPreferences.getString("name", null)
    val surname = sharedPreferences.getString("surname", null)
    val age = sharedPreferences.getInt("age", -1)
    val imageId = sharedPreferences.getString("imageId", "laza_profilna")
    val description = sharedPreferences.getString("description", "")
    val nickname = sharedPreferences.getString("nickname", null)
    val id = sharedPreferences.getLong("id", -1)



    Log.d(
        "1.getUserFromSharedPreferences",
        "User: $name $surname $age $imageId $description $nickname  $id"
    )


    if (name == null || surname == null || age == -1) return null // Basic validation


    Log.d(
        "2.getUserFromSharedPreferences",
        "User: $name $surname $age $imageId $description $nickname  $id"
    )

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
fun ProfileScreen(navController: NavHostController) {

    val user = getUserFromSharedPreferences(LocalContext.current)


    user?.let { currentUser ->
        ProfileContent(currentUser, PaddingValues(top = 100.dp), navController)
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("No user data available", style = MaterialTheme.typography.bodyLarge)
    }
}


@SuppressLint("DiscouragedApi")
@Composable
fun ProfileContent(user: User, innerPadding: PaddingValues, navController: NavHostController) {

    user.description = user.description.replace("\n", "")

    val cardElevation = CardDefaults.cardElevation(
        defaultElevation = 8.dp,
        pressedElevation = 12.dp,
        focusedElevation = 10.dp,
        hoveredElevation = 10.dp
    )


    val realId = LocalContext.current.resources.getIdentifier(
        user.image_id,
        "drawable",
        LocalContext.current.packageName
    )

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
            modifier = Modifier
                .padding(horizontal = 16.dp)

            ) {


            Box(modifier = Modifier.size(200.dp)) {
                Image(
                    painter = painterResource(id = realId), contentDescription = "",

                    modifier = Modifier
                        .size(200.dp)
                        .clip(MaterialTheme.shapes.large),
                    contentScale = ContentScale.Crop
                )

            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(250.dp)

            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = user.surname, style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }

        }


        Spacer(modifier = Modifier.size(16.dp))


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier =
            Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),

            ) {


            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 4.dp, top = 4.dp, start = 4.dp, end = 8.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = cardElevation

            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(bottom = 8.dp, top = 0.dp, start = 8.dp, end = 8.dp)
                        .align(Alignment.CenterHorizontally),
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
                    .weight(1f)
                    .padding(bottom = 4.dp, top = 4.dp, start = 8.dp, end = 4.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = cardElevation

            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(bottom = 8.dp, top = 0.dp, start = 8.dp, end = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Godine\u00A0",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)


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
            elevation = cardElevation
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
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(16.dp)
                )

            }

        }

        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically


        ) {


            Button(
                onClick = { },
                shape = MaterialTheme.shapes.medium,

                ) {
                Text(
                    text = "Update profile",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

            }
            Spacer(modifier = Modifier.width(8.dp))

            val current = LocalContext.current

            Button(
                onClick = {
                    val sharedPreferences =
                        current.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear()
                    editor.apply()


                    // Ensure you are navigating correctly and clearing the back stack.
                    navController.popBackStack("Home", false)
                    navController.navigate("Login") {
                        // This clears everything up to the "login" destination and avoids creating a back stack entry for the login screen
                        popUpTo("Home") {
                            inclusive = true
                        }
                    }


                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = "Logout",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

            }

        }


    }

}

@Composable
@Preview
fun ProfileScreenPreview() {

}

