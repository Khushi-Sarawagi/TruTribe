package com.example.trutribe.ui

import android.os.Bundle
import android.content.Intent
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trutribe.R
import com.google.android.material.tabs.TabLayout
import com.example.trutribe.models.CommunityModel
import com.example.trutribe.api.RetrofitClient
import com.example.trutribe.adapters.CommunityAdapter
import java.util.ArrayList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast

class CommunityPageActivity : AppCompatActivity() {
    private lateinit var trendingRecyclerView: RecyclerView
    private lateinit var suggestedRecyclerView: RecyclerView
    private lateinit var myCommunityRecyclerView: RecyclerView
    private lateinit var trendingAdapter: CommunityAdapter
    private lateinit var suggestedAdapter: CommunityAdapter
    private lateinit var myCommunityAdapter: CommunityAdapter
    private lateinit var communitylist: ArrayList<CommunityModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        val bottomNavBar = findViewById<TabLayout>(R.id.tablayout)
        trendingRecyclerView = findViewById(R.id.trending_community)
        suggestedRecyclerView = findViewById(R.id.suggested_community)
        myCommunityRecyclerView = findViewById(R.id.my_community)
        trendingRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        suggestedRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        myCommunityRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        fetchTrendingCommunities()
        fetchSuggestedCommunities()
        fetchMyCommunities()
    }

    private fun fetchTrendingCommunities() {
        val call = RetrofitClient.instance.getTrendingCommunities()

        call.enqueue(object : Callback<List<CommunityModel>> {
            override fun onResponse(call: Call<List<CommunityModel>>, response: Response<List<CommunityModel>>) {
                if (response.isSuccessful) {
                    val trendingList = response.body() ?: ArrayList()
                    trendingAdapter = CommunityAdapter(ArrayList(trendingList)) { selectedCommunity ->
                        launchQuizActivity(selectedCommunity.dataId)
                    }
                    trendingRecyclerView.adapter = trendingAdapter
                } else {
                    Toast.makeText(this@CommunityPageActivity, "Failed to load trending communities", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CommunityModel>>, t: Throwable) {
                Toast.makeText(this@CommunityPageActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchSuggestedCommunities() {
        val call = RetrofitClient.instance.getSuggestedCommunities()

        call.enqueue(object : Callback<List<CommunityModel>> {
            override fun onResponse(call: Call<List<CommunityModel>>, response: Response<List<CommunityModel>>) {
                if (response.isSuccessful) {
                    val suggestedList = response.body() ?: ArrayList()
                    suggestedAdapter = CommunityAdapter(ArrayList(suggestedList)) { selectedCommunity ->
                        launchQuizActivity(selectedCommunity.dataId)
                    }
                    suggestedRecyclerView.adapter = suggestedAdapter
                } else {
                    Toast.makeText(this@CommunityPageActivity, "Failed to load suggested communities", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CommunityModel>>, t: Throwable) {
                Toast.makeText(this@CommunityPageActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchMyCommunities() {
        val call = RetrofitClient.instance.getMyCommunities()

        call.enqueue(object : Callback<List<CommunityModel>> {
            override fun onResponse(call: Call<List<CommunityModel>>, response: Response<List<CommunityModel>>) {
                if (response.isSuccessful) {
                    val myCommunityList = response.body() ?: ArrayList()
                    myCommunityAdapter = CommunityAdapter(ArrayList(myCommunityList)) { selectedCommunity ->
                        launchQuizActivity(selectedCommunity.dataId)
                    }
                    myCommunityRecyclerView.adapter = myCommunityAdapter
                }
            }

            override fun onFailure(call: Call<List<CommunityModel>>, t: Throwable) {
                Toast.makeText(this@CommunityPageActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun launchQuizActivity(selectedQuizId: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("quizId", selectedQuizId)
        startActivity(intent)
    }
}
