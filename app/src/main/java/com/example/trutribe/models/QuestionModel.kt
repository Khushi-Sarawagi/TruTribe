package com.example.trutribe.models

data class QuestionModel(
    val question_id: Int,
    val quiz_id: Int,
    val community_id: Int,
    val question_text: String,
    val option_a: String,
    val option_b: String,
    val option_c: String,
    val option_d: String,
    val correct_option: String
)
