package com.example.eventapp

import retrofit2.http.GET

import retrofit2.http.Body
import retrofit2.http.POST

data class User(
    val id: Long,
    val name: String,
    val surname: String,
    val password: String,
    val age: Int,
    val image_id: Int,
    val description: String,
    val nickname: String?
)


interface UserService {
    @GET("/users")
    suspend fun getAllUsers(): List<User>
}






interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): retrofit2.Response<User>

    @GET("/users/me")  // Example endpoint that returns details of the current logged-in user
    suspend fun getUser(): retrofit2.Response<User>

}


data class LoginRequest(
    val name: String,
    val password: String
)


data class LoginResponse(val message: String)