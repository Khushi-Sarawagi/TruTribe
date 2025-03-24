package com.example.trutribe.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// API endpoints
interface ApiService {
    @POST("/signup")
    fun signup(@Body userData: UserData): Call<ApiResponse>

    @POST("/login")
    fun login(@Body userData: UserData): Call<ApiResponse>
}
