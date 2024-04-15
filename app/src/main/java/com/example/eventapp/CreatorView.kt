package com.example.eventapp


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog
import com.marosseleng.compose.material3.datetimepickers.time.ui.TimePicker
import com.marosseleng.compose.material3.datetimepickers.time.ui.dialog.TimePickerDialog
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreatorScreen(innerPadding: PaddingValues, navController: NavHostController) {

    var date by remember { mutableStateOf(LocalDate.now()) }
    var time by remember {
        mutableStateOf(LocalTime.now())
    }

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val calendar = remember { Calendar.getInstance() }
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    val items = listOf("Biljar", "Kof", "Mesina", "Ostalo")




    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

    ) {

        Spacer(modifier = Modifier.height(50.dp))


            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(0.75f)
                ) {



            TextField(value = items[selectedIndex], onValueChange = {}, label = {

                Text(text = "Event Type", style = MaterialTheme.typography.bodyMedium)

            }, readOnly = true,
             modifier = Modifier.fillMaxWidth(0.6f)

            )

            Button(
                onClick = {  expanded = true},
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = MaterialTheme.shapes.medium,


                ) {
                Text(
                    text = "Select Type",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    items.forEachIndexed { index, s ->

                        DropdownMenuItem(text = { Text(s) },
                            onClick = {
                                selectedIndex = index
                                expanded = false})

                    }



                }

            }

            }






        TextField(value = "", onValueChange = { /*TODO*/ }, label = {

            Text(
                text = "Event Name",
                style = MaterialTheme.typography.bodyMedium
            )

        },
            modifier = Modifier.fillMaxWidth(0.75f)
        )

        Box(
            modifier = Modifier
                .clickable(onClick = { showDialog = true })
                .fillMaxWidth(0.75f)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

            ) {


                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.6f),
                    value = date.format(DateTimeFormatter.ofPattern("d MMMM yyyy")),
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Date") },

                    )

                Button(
                    onClick = {  showDialog = true },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    shape = MaterialTheme.shapes.medium,


                    ) {
                    Text(
                        text = "Select Date",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                }


            }

        }


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(0.75f),
            verticalAlignment = Alignment.CenterVertically

        ) {


            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                value = time.format(DateTimeFormatter.ofPattern("HH:mm")),
                onValueChange = { },
                readOnly = true,
                label = { Text("Hour") },

                )

            Button(
                onClick = { showTimePicker = true},
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = MaterialTheme.shapes.medium,


                ) {
                Text(
                    text = "Select Hour",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

            }


        }



        if (showTimePicker) {
            TimePickerDialog(onDismissRequest = { showTimePicker = false },
                onTimeChange ={time = it},
                is24HourFormat = false,
                initialTime = LocalTime.now(),
                title = {Text("Select Time")},

                )



        }



        TextField(
            value = "",
            onValueChange = { },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth(.75f)
                .height(150.dp), // Adjust the height as needed
            maxLines = 5// Allow as many lines as needed
        )
        Row(
            horizontalArrangement = Arrangement.Start
            , modifier = Modifier.fillMaxWidth(0.75f)

        ) {


            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(0.5f),
                shape = MaterialTheme.shapes.medium,


                ) {
                Text(
                    text = "Submit",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )

            }

        }

    }


}

@Preview(showBackground = true)
@Composable
fun PreviewCreatorScreen() {
    CreatorScreen(innerPadding = PaddingValues(0.dp), navController = rememberNavController())
}
