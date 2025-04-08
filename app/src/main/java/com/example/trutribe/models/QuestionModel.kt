package com.example.trutribe.models
import com.google.gson.annotations.SerializedName
data class QuestionModel(
    val question_id: Int,
    val quiz_id: Int,
    val community_id: Int,
    val question_text: String,
    val options:Options,
    val correct_option: String
)
data class Options(
    @SerializedName("A") val a: String,
    @SerializedName("B") val b: String,
    @SerializedName("C") val c: String,
    @SerializedName("D") val d: String
)
