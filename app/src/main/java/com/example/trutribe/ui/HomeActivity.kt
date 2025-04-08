package com.example.trutribe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trutribe.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home) // Load your homefragment.xml here
    }
}
