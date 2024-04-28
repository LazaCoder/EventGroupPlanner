package com.example.eventapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl("https://eventapp-backend-production-baa5.up.railway.app") // Replace with your actual URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }
}