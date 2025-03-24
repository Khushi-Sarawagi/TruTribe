package com.example.trutribe.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// API endpoints
interface ApiService {


    @POST("/login")
    fun login(@Body userData: UserData): Call<ApiResponse>
}