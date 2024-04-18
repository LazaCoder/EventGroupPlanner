package com.example.eventapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://asa-zda75gjltbteu-standard-apper.azuremicroservices.io" // Use 10.0.2.2 for localhost in Android emulator

    val instance: UserService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(UserService::class.java)
    }
}


object RetrofitClient2 {
    private const val BASE_URL = "https://asa-zda75gjltbteu-standard-apper.azuremicroservices.io" // Use 10.0.2.2 for localhost in Android emulator

    val instance: EventService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(EventService::class.java)
    }
}


object RetrofitClient3 {
    private const val BASE_URL = "https://asa-zda75gjltbteu-standard-apper.azuremicroservices.io"

    val instance: EventService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EventService::class.java)
    }
}