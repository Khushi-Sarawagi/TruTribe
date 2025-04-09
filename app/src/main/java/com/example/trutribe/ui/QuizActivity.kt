package com.example.trutribe.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.trutribe.R
import com.example.trutribe.models.QuestionModel
import com.example.trutribe.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizActivity : AppCompatActivity() {

    private lateinit var communityTitleTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var questionNumberTextView: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var option3: RadioButton
    private lateinit var option4: RadioButton
    private lateinit var submitButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var timerText: TextView

    private var questionList = ArrayList<QuestionModel>()
    private var currentQuestionIndex = 0
    private var score = 0
    private var attempts = 3
    private val totalQuestions = 5

    private var answerSubmitted = false

    private var timer: CountDownTimer? = null
    private val timerDuration = 30000L
    private var communityId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        communityTitleTextView = findViewById(R.id.community_title)
        questionTextView = findViewById(R.id.question)
        questionNumberTextView = findViewById(R.id.ques_no)
        radioGroup = findViewById(R.id.radioGroup)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        submitButton = findViewById(R.id.submitBtn)
        progressBar = findViewById(R.id.progress_calculate)
        timerText = findViewById(R.id.timer_text)

        communityTitleTextView.text = intent.getStringExtra("COMMUNITY_NAME")
        communityId = intent.getIntExtra("communityId", -1)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->

            if(checkedId != -1) {
                resetButtonColors()
                val selectedRadioButton = findViewById<RadioButton>(checkedId)
                selectedRadioButton.setBackgroundResource(R.drawable.selected_button)
            }
        }

        submitButton.setOnClickListener {
            val selectedRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            if (!answerSubmitted) {
                if (selectedRadioButton != null) {

                    checkAnswer(questionList[currentQuestionIndex].correct_option)
                    answerSubmitted = true
                    submitButton.text = if (currentQuestionIndex == totalQuestions - 1) "Finish" else "Next"
                } else {
                    Toast.makeText(this, "Please select an option!", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (currentQuestionIndex < totalQuestions - 1) {
                    currentQuestionIndex++
                    loadNextQuestion()
                    answerSubmitted = false
                    submitButton.text = "Submit"
                } else {
                    checkResults()  // Directly check results without increasing index
                }
            }
        }

        fetchQuestions()
    }

    private fun fetchQuestions() {
        val call = RetrofitClient.instance.getQuizQuestions(communityId)
        call.enqueue(object : Callback<List<QuestionModel>> {
            override fun onResponse(call: Call<List<QuestionModel>>, response: Response<List<QuestionModel>>) {
                if (response.isSuccessful) {
                    val allQuestions = response.body() ?: ArrayList()
                    if (allQuestions.size < totalQuestions) {
                        Toast.makeText(this@QuizActivity, "Not enough questions to start the quiz.", Toast.LENGTH_LONG).show()
                        navigateToCommunityPage()
                        return
                    }

                    questionList = ArrayList(allQuestions.shuffled().take(totalQuestions))
                    loadNextQuestion()
                } else {

                    Toast.makeText(this@QuizActivity, "Failed to load questions!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<QuestionModel>>, t: Throwable) {
                Toast.makeText(this@QuizActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadNextQuestion() {
        if (currentQuestionIndex < questionList.size) {
            val question = questionList[currentQuestionIndex]
            questionNumberTextView.text = "Question ${currentQuestionIndex + 1} of $totalQuestions"
            questionTextView.text = question.question_text
            option1.text = question.options.a
            option2.text = question.options.b
            option3.text = question.options.c
            option4.text = question.options.d

            radioGroup.clearCheck()
            enableOptions()
            resetButtonColors()
            startTimer()
            updateProgressBar()
        } else {
            checkResults()
        }
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(timerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                timerText.text = "Time Left: ${secondsRemaining}s"
            }

            override fun onFinish() {
                timerText.text = "Time's up!"
                val correctOption = questionList[currentQuestionIndex].correct_option
                val selectedId = radioGroup.checkedRadioButtonId

                checkAnswer(correctOption)
                answerSubmitted = true
                submitButton.text = if (currentQuestionIndex == totalQuestions - 1) "Finish" else "Next"
            }
        }.start()
    }

    private fun updateProgressBar() {
        val progress = (((currentQuestionIndex+1).toFloat() / totalQuestions) * 100).toInt()
        progressBar.progress = progress
    }

    private fun checkAnswer(correctOption: String) {
        timer?.cancel()

        val selectedId = radioGroup.checkedRadioButtonId
        val selectedRadioButton = if (selectedId != -1) findViewById<RadioButton>(selectedId) else null
        // Removed resetButtonColors() here so the blue (selected) state is not overwritten
        // resetButtonColors()

        val selectedOptionKey = when (selectedRadioButton) {
            option1 -> "A"
            option2 -> "B"
            option3 -> "C"
            option4 -> "D"
            else -> ""
        }

        if (selectedOptionKey == correctOption) {
            selectedRadioButton?.setBackgroundResource(R.drawable.correct_button)
            score++
        } else {
            selectedRadioButton?.setBackgroundResource(R.drawable.wrong_button)
            highlightCorrectAnswer(correctOption)
        }

        disableOptions()
    }
    /*private fun checkAnswer(correctOption: String) {
        timer?.cancel()

        val selectedId = radioGroup.checkedRadioButtonId
        val selectedRadioButton = if (selectedId != -1) findViewById<RadioButton>(selectedId) else null
        resetButtonColors()

        val selectedOptionKey = when (selectedRadioButton) {
            option1 -> "A"
            option2 -> "B"
            option3 -> "C"
            option4 -> "D"
            else -> ""
        }

        if (selectedOptionKey == correctOption) {
            selectedRadioButton?.setBackgroundResource(R.drawable.correct_button)
            score++
        } else {
            selectedRadioButton?.setBackgroundResource(R.drawable.wrong_button)
            highlightCorrectAnswer(correctOption)
        }

        disableOptions()
    }*/
    /*private fun checkAnswer(correctOption: String) {
        timer?.cancel()

        val selectedId = radioGroup.checkedRadioButtonId
        val selectedRadioButton = if (selectedId != -1) findViewById<RadioButton>(selectedId) else null
        resetButtonColors()

        if (selectedOption == correctOption) {
            selectedRadioButton?.setBackgroundResource(R.drawable.correct_button)
            score++
        } else {
            selectedRadioButton?.setBackgroundResource(R.drawable.wrong_button)
            highlightCorrectAnswer(correctOption)
        }

        disableOptions()
    }*/

    private fun highlightCorrectAnswer(correctOption: String) {
        val correctButton = when (correctOption) {
            "A" -> option1
            "B" -> option2
            "C" -> option3
            "D" -> option4
            else -> null
        }
        correctButton?.setBackgroundResource(R.drawable.correct_button)
    }

    /*private fun highlightCorrectAnswer(correctOption: String) {
        val correctButton = when (correctOption) {
            option1.text.toString() -> option1
            option2.text.toString() -> option2
            option3.text.toString() -> option3
            option4.text.toString() -> option4
            else -> null
        }
        correctButton?.setBackgroundResource(R.drawable.correct_button)
    }*/

    private fun disableOptions() {
        option1.isEnabled = false
        option2.isEnabled = false
        option3.isEnabled = false
        option4.isEnabled = false
    }

    private fun enableOptions() {
        option1.isEnabled = true
        option2.isEnabled = true
        option3.isEnabled = true
        option4.isEnabled = true
    }

    private fun resetButtonColors() {
        option1.setBackgroundResource(R.drawable.option_box)
        option2.setBackgroundResource(R.drawable.option_box)
        option3.setBackgroundResource(R.drawable.option_box)
        option4.setBackgroundResource(R.drawable.option_box)
    }

    private fun checkResults() {
        if (score >= 3) {
            showPassDialog()
        } else {
            if (attempts > 1) {
                attempts--
                showFailDialog()
            } else {
                Toast.makeText(this, "No more attempts left! Try later.", Toast.LENGTH_SHORT).show()
                navigateToCommunityPage()
            }
        }
    }

    private fun showPassDialog() {
        AlertDialog.Builder(this)
            .setTitle("Congratulations!")
            .setMessage("You passed the quiz. Join the community now!")
            .setPositiveButton("Join") { _, _ -> navigateToCommunityPage() }
            .setCancelable(false)
            .show()
    }

    private fun showFailDialog() {
        AlertDialog.Builder(this)
            .setTitle("Try Again")
            .setMessage("You didn't score enough. Try again or come back later.")
            .setPositiveButton("Try Again") { _, _ ->
                currentQuestionIndex = 0
                score = 0
                fetchQuestions()
            }
            .setNegativeButton("Try Later") { _, _ ->
                navigateToCommunityPage()
            }
            .setCancelable(false)
            .show()
    }

    private fun navigateToCommunityPage() {
        val intent = Intent(this, CommunityPageActivity::class.java)
        startActivity(intent)
        finish()
    }
}