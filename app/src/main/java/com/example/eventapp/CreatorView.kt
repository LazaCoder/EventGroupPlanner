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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreatorScreen(innerPadding: PaddingValues, navController: NavHostController) {

    var date by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    // Remembering the selected date to persist over recompositions
    val calendar = remember { Calendar.getInstance() }
    val year = remember { calendar.get(Calendar.YEAR) }
    val month = remember { calendar.get(Calendar.MONTH) }
    val day = remember { calendar.get(Calendar.DAY_OF_MONTH) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.medium)
                .padding(16.dp)
                .fillMaxWidth(.75f),
            contentAlignment = Alignment.Center,


            ) {

            Text(
                text = "Create Your Event",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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

        if (showDialog) {

            DatePickerDialog(onDismissRequest = { showDialog = false },
                onDateChange = {
                    date = it
                    showDialog = false
                },
                today = LocalDate.now(),
                title = {
                    Text("Select Date")

                }
            )


        }



        TextField(
            value = "",
            onValueChange = { },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth(.75f)
                .height(200.dp), // Adjust the height as needed
            maxLines = Int.MAX_VALUE // Allow as many lines as needed
        )


    }


}

@Preview(showBackground = true)
@Composable
fun PreviewCreatorScreen() {
    CreatorScreen(innerPadding = PaddingValues(0.dp), navController = rememberNavController())
}
