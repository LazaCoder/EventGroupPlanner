package com.example.eventapp

import android.provider.ContactsContract.Profile
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun ProfileScreen(innerPadding: PaddingValues, navController: NavController) {

    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,) {

        Text(
            text = "Profile",
            fontSize = 24.sp,
        )

    }


}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {

    ProfileScreen(innerPadding = PaddingValues(0.dp), navController = rememberNavController())

}