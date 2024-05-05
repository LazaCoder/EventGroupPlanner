package com.example.eventapp

import Message
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.google.gson.Gson
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.postgresListDataFlow
import kotlinx.coroutines.Job
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put
import supabase
import java.time.LocalDateTime

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.asStateFlow()
    private var channelSubscription: Job? = null

    init {
        loadMessages()
        subscribeToUpdates()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            try {
                val response = supabase.from("messages").select(columns = Columns.ALL)
                response.data.let {
                    val fetchedMessages = Gson().fromJson(it, Array<Message>::class.java).toList()
                    _messages.value = fetchedMessages

                    Log.d("ChatViewModel", "Messages loaded")
                    Log.d(channelSubscription?.isActive.toString(),channelSubscription?.isCompleted.toString())
                }
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error loading messages: $e")
            }
        }
    }

    fun subscribeToUpdates() {
        channelSubscription = viewModelScope.launch {
            val channel = supabase.channel("messages")

            try {

                channel.broadcast(event = "message", message = buildJsonObject { put("a", "a") });
                val changeFlow = channel.postgresChangeFlow<PostgresAction>(schema = "public") {
                    table = "messages"
                }

                channel.subscribe(blockUntilSubscribed = true)

                Log.d("ChatViewModel", "Channel subscribed")

                changeFlow.collect { postgresAction ->

                    Log.d("ChatViewModel", "Received PostgresAction: $postgresAction")
                    when (postgresAction) {
                        is PostgresAction.Insert -> {
                            val jsonRecord = postgresAction.record.jsonObject
                            val jsonString = jsonRecord.toString()
                            Log.d("ChatViewModel", "Received new message: $jsonString")
                            try {
                                val message = Gson().fromJson(jsonString, Message::class.java)
                                val updatedMessages = _messages.value.toMutableList()
                                updatedMessages.add(message)
                                _messages.value = updatedMessages.toList()
                                Log.d("ChatViewModel", "Message added to the list")
                            } catch (e: Exception) {
                                Log.e("ChatViewModel", "Error parsing message: $e")
                            }
                        }
                        is PostgresAction.Delete -> {

                            loadMessages()

                        }



                        else -> Log.d("ChatViewModel", "Unhandled PostgresAction: $postgresAction")
                    }
                }



            } catch (e: Exception) {
                Log.e("ChatViewModel", "Subscription error: $e")
            }
        }
    }

    fun sendMessage(messageText: String, context: Context) {
        viewModelScope.launch {
            val user = getUserFromSharedPreferences(context)  // Adjust according to your app's context handling
            user?.let {
                val messageObj = Message(
                    sender_id = it.id,
                    message_text = messageText,
                    timestamp = LocalDateTime.now().toString(),
                    sender_name = it.name
                )
                try {
                    supabase.from("messages").insert(messageObj)
                    Log.d("ChatViewModel", "Message sent")


                } catch (e: Exception) {
                    Log.e("ChatViewModel", "Failed to send message: $e")
                }
            }
        }
    }

}