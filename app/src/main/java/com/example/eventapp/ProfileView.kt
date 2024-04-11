package com.example.eventapp

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun ProfileScreen(innerPadding: PaddingValues, navController: NavController) {

    var name = "Ivan"
    var surname = "Lazarušić"
    var imageId = R.drawable.laza_profilna

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
                painter = painterResource(id = imageId), contentDescription = "",

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
                    text = name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(text = surname, style = MaterialTheme.typography.headlineLarge,
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

                  Text(
                      text = "Laza",
                      style = MaterialTheme.typography.headlineSmall,

                      )

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
                      text = "23",
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
                    text = "Student sam treće godine Fakulteta tehničkih nauka u Novom Sadu. " +
                            "Volim da se bavim programiranjem i da učim nove stvari. " +
                            "Trenutno sam na praksi u kompaniji Vega IT Sourcing.",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

            }

        }


    }





}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {

    ProfileScreen(innerPadding = PaddingValues(0.dp), navController = rememberNavController())

}