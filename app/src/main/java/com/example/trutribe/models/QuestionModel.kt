package com.example.trutribe.models

data class QuestionModel(
    val questionText: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctOption: String
)
