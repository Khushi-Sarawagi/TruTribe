package com.example.trutribe.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trutribe.R

class JoinedCommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.joined_community_page)

        val communityId = intent.getIntExtra("communityId", -1)
        // TODO: fetch & display details for this communityId
    }
}
