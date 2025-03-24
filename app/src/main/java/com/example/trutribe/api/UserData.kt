package com.example.trutribe.api

data class UserData(
    val email: String,
    val password: String
)

data class ApiResponse(
    val message: String
)