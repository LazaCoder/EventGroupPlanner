package com.example.eventapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDate


class EventViewModel : ViewModel() {
    private val _events = mutableStateOf<List<Event>>(emptyList())
    val events: State<List<Event>> = _events
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun loadEvents(date: LocalDate) {
        viewModelScope.launch {
            _isLoading.value = true
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
            } finally {
                _isLoading.value = false
            }
        }
    }
}
