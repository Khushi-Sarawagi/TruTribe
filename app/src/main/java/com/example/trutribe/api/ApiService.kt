package com.example.trutribe.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import com.example.trutribe.models.QuestionModel
import com.example.trutribe.models.CommunityModel


// API endpoints
interface ApiService {
    @POST("/signup")
    fun signup(@Body userData: UserData): Call<ApiResponse>

    @POST("/login")
    fun login(@Body userData: LoginData): Call<ApiResponse>

    @GET("/communities/trending")
    fun getTrendingCommunities(): Call<List<CommunityModel>>

    @GET("/communities/suggested")
    fun getSuggestedCommunities(): Call<List<CommunityModel>>

    @GET("my_communities")
    fun getMyCommunities(): Call<List<CommunityModel>>

    @GET("quiz/questions")
    fun getQuizQuestions(): Call<List<QuestionModel>>
}
