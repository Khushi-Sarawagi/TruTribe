package com.example.trutribe.models

data class QuestionModel(
    val question_id: Int,
    val quiz_id: Int,
    val community_id: Int,
    val question_text: String,
    val options:Options,
    val correct_option: String
)
data class Options(
    val a: String,
    val b: String,
    val c: String,
    val d: String
)
