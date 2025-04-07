package com.example.trutribe.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
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
    private var selectedOption = ""

    private var timer: CountDownTimer? = null
    private val timerDuration = 30000L

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


        val communityName = intent.getStringExtra("COMMUNITY_NAME")
        communityTitleTextView.text = communityName

        fetchQuestions()
    }

    /*private fun fetchQuestions() {

        questionList = arrayListOf(
            QuestionModel(
                "What is Kotlin?",
                "A programming language",
                "A database",
                "An IDE",
                "A framework",
                "A programming language"
            ),
            QuestionModel(
                "Which company developed Android?",
                "Apple",
                "Google",
                "Microsoft",
                "Samsung",
                "Google"
            ),
            QuestionModel(
                "What does API stand for?",
                "Advanced Programming Interface",
                "Application Programming Interface",
                "Automated Program Integration",
                "Android Programming Interface",
                "Application Programming Interface"
            ),
            QuestionModel(
                "What is Android Studio?",
                "Text Editor",
                "IDE",
                "Programming Language",
                "Database",
                "IDE"
            ),
            QuestionModel(
                "What is the extension of Kotlin files?",
                ".java",
                ".kt",
                ".xml",
                ".kotlin",
                ".kt"
            )
        )

        // Shuffle and take 5 random questions
        questionList.shuffle()
        loadNextQuestion()
    }*/
    private fun fetchQuestions() {
        val call = RetrofitClient.instance.getQuizQuestions()
        call.enqueue(object : Callback<List<QuestionModel>> {
            override fun onResponse(
                call: Call<List<QuestionModel>>,
                response: Response<List<QuestionModel>>
            ) {
                if (response.isSuccessful) {
                    val allQuestions = response.body() ?: ArrayList()

                    // Shuffle and take 5 random questions
                    questionList = ArrayList(allQuestions.shuffled().take(totalQuestions))
                    loadNextQuestion()
                } else {
                    Toast.makeText(
                        this@QuizActivity,
                        "Failed to load questions!",
                        Toast.LENGTH_SHORT
                    ).show()
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
            questionTextView.text = question.questionText
            option1.text = question.option1
            option2.text = question.option2
            option3.text = question.option3
            option4.text = question.option4


            radioGroup.clearCheck()
            enableOptions()


            startTimer()
            updateProgressBar()


            submitButton.setOnClickListener {
                checkAnswer(question.correctOption)
            }
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
                val progress = ((millisUntilFinished.toFloat() / timerDuration) * 100).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                timerText.text = "Time's up!"
                checkAnswer("")
            }
        }
        timer?.start()
    }

    private fun updateProgressBar() {
        val progress = ((currentQuestionIndex.toFloat() / totalQuestions) * 100).toInt()
        progressBar.progress = progress
    }

    private fun checkAnswer(correctOption: String) {
        val selectedRadioButton = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
        if (selectedRadioButton != null) {
            resetButtonColors()
            selectedRadioButton.setBackgroundResource(R.drawable.selected_button)

            submitButton.text = "Next"
            submitButton.setOnClickListener {
                if (selectedOption == correctOption) {
                    selectedRadioButton.setBackgroundResource(R.drawable.correct_button)
                    score++
                } else {
                    selectedRadioButton.setBackgroundResource(R.drawable.wrong_button)
                    highlightCorrectAnswer(correctOption)
                }

                submitButton.postDelayed({
                    currentQuestionIndex++
                    loadNextQuestion()
                }, 1000)
            }
        } else {
            Toast.makeText(this, "Please select an option!", Toast.LENGTH_SHORT).show()
            return
        }

        disableOptions()
    }

    private fun highlightCorrectAnswer(correctOption: String) {
        val correctButton = when (correctOption) {
            option1.text -> option1
            option2.text -> option2
            option3.text -> option3
            option4.text -> option4
            else -> null
        }
        correctButton?.setBackgroundResource(R.drawable.correct_button)
    }

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
        resetButtonColors()
    }

    private fun resetButtonColors() {
        option1.setBackgroundResource(R.drawable.option_box)
        option2.setBackgroundResource(R.drawable.option_box)
        option3.setBackgroundResource(R.drawable.option_box)
        option4.setBackgroundResource(R.drawable.option_box)
    }

    private fun highlightSelectedOption(selectedRadioButton: RadioButton) {
        resetButtonColors()
        selectedRadioButton.setBackgroundResource(R.drawable.selected_button)  // New blue color for selected option
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
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Congratulations!")
        builder.setMessage("You passed the quiz. Join the community now!")
        builder.setPositiveButton("Join") { _, _ ->
            navigateToCommunityPage()
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun showFailDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Try Again")
        builder.setMessage("You didn't score enough. Try again or come back later.")
        builder.setPositiveButton("Try Again") { _, _ ->
            currentQuestionIndex = 0
            score = 0
            fetchQuestions()
        }
        builder.setNegativeButton("Try Later") { _, _ ->
            navigateToCommunityPage()
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun navigateToCommunityPage() {
        val intent = Intent(this, CommunityPageActivity::class.java)
        startActivity(intent)
        finish()
    }
}
