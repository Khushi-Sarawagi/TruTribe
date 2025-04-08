package com.example.trutribe.api

data class UserData(
    val username: String,
    val email: String,
    val password: String
)

data class LoginData(
    val username: String,
    val password: String
)

data class ApiResponse(
    val message: String
)