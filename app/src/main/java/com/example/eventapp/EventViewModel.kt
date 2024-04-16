package com.example.eventapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalDate


class EventViewModel : ViewModel() {
    private val _events = mutableStateOf<List<Event>>(emptyList())
    val events: State<List<Event>> = _events

    fun loadEvents(date: LocalDate) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient2.instance.getEventsForDate(date)
                if (response.isSuccessful && response.body() != null) {
                    _events.value = response.body()!!
                } else {
                    // Handle errors or empty states

                    println("Nesto ne stima")
                    _events.value = listOf()
                }
            } catch (e: Exception) {
                println("GRESKA NEKA")

                println(e.message)

                _events.value = listOf()
            }
        }
    }
}
