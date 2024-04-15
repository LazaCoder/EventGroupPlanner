package com.example.eventapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class EventViewModel : ViewModel() {
    private val _events = mutableStateOf<List<Event>>(listOf())
    val events: State<List<Event>> = _events

    fun loadEvents(date: String) {
        viewModelScope.launch {
            val response = try {
                RetrofitClient.instance.getEventsForDate(date)
            } catch (e: Exception) {
                Response.error(404, okhttp3.ResponseBody.create(null, ""))
            }

            if (response.isSuccessful && response.body() != null) {
                _events.value = response.body()!!
            } else {
                // Handle errors or empty states
                _events.value = listOf()
            }
        }
    }
}