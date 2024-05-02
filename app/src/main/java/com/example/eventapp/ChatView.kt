import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ChatScreen(paddingValues: PaddingValues) {
    val exampleMessage = Message("Hello, I am Alice!", "Alice", LocalTime.now())

    Scaffold(
        bottomBar = { InputBar() } // Bottom bar for user input
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(), // Adjust bottom padding to avoid overlapping
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(20) {
                item {
                    MessageView(exampleMessage)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBar() {
    var text = remember { mutableStateOf("") }

    // Styling the input field
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 80.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text.value,
                onValueChange = { text.value = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = false,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    // Implement sending logic here
                    // For now, we just clear the field
                    text.value = ""
                })
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    // Implement sending logic here
                    text.value = ""
                }
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun MessageView(message: Message) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth() // Adjusted to fill max width instead of fillMaxSize for better control in LazyColumn
    ) {
        Text(
            text = message.sender,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 2.dp, start = 4.dp) // Added some padding below the sender name
        )

        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.inversePrimary ) // Background now applied directly to Box
                .wrapContentWidth()
        ) {
            Text(
                text = message.message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(all = 16.dp)
            )

            Text(
                text = formatMessageTime(message.time),
                style = MaterialTheme.typography.bodySmall,  // Use a smaller text style
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 40.dp,end = 12.dp, bottom = 4.dp)
            )




        }




    }
}

fun formatMessageTime(time: LocalTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")  // Format for hour and minute
    return time.format(formatter)
}

data class Message(
    val message: String,
    val sender: String,
    val time: LocalTime
)

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen(PaddingValues(16.dp))
}