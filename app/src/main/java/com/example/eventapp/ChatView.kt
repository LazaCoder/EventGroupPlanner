import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventapp.ChatViewModel

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

@Composable
fun ChatScreen(paddingValues: PaddingValues, viewModel: ChatViewModel) {
    val messages by viewModel.messages.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var textFieldState by remember { mutableStateOf(TextFieldValue()) }

    Log.d("ChatScreen", "Recomposing with ${messages.size} messages")

    val context = LocalContext.current

    LaunchedEffect(messages.size) {
        coroutineScope.launch {

            if(messages.isNotEmpty()){
            listState.animateScrollToItem(messages.size - 1) }
        }
    }
    Scaffold(
        modifier = Modifier.padding(bottom = 70.dp),

             // Bottom bar for user input
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding(), // Adjust bottom padding to avoid overlapping
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(messages.size) { index ->
                MessageView(messages[index])


            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBar(onSendMessage: (String) -> Unit) {
    var text = remember { mutableStateOf("") }

    // Styling the input field
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, bottom = 0.dp),
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
                    onSendMessage(text.value)
                    text.value = ""
                })
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {


                   onSendMessage(text.value)
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
            text = message.sender_name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 2.dp, start = 4.dp) // Added some padding below the sender name
        )

        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.inversePrimary) // Background now applied directly to Box
                .wrapContentWidth()
        ) {
            Text(
                text = message.message_text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(all = 16.dp)
            )

            Text(
                text = formatMessageTime(message.timestamp.toLocalDateTime()),
                style = MaterialTheme.typography.bodySmall,  // Use a smaller text style
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 40.dp, end = 12.dp, bottom = 4.dp)
            )




        }




    }
}

fun formatMessageTime(time: kotlinx.datetime.LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")  // Format for hour and minute

    return time.time.toString()
}




@Serializable
data class Message(
    @SerializedName("message_id")
    val message_id: Long? = null,

    @SerializedName("sender_id")
    val sender_id: Long,

    @SerializedName("message_text")
    val message_text: String,

    @SerializedName("timestamp")
    val timestamp: String,

    @SerializedName("sender_name")
    val sender_name: String
)


@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {

}