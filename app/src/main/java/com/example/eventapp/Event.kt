package com.example.eventapp
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call
data class Event(
    val eventId: Int,
    val createdById: Int,
    val eventName: String,
    val eventDate: String,
    val eventDescription: String,
    val eventType: String
)





interface EventService {
    @GET("events/date/{date}")
    fun getEventsForDate(@Path("date") date: String): Call<List<Event>>
}