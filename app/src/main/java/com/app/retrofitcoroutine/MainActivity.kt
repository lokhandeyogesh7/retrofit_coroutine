package com.app.retrofitcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.app.retrofitcoroutine.networking.ApiProvider
import com.app.retrofitcoroutine.networking.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var apiService: ApiService? = null
    private var coroutineJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiService = ApiProvider.createService(ApiService::class.java)

        searchButton.setOnClickListener {
            val timer = object: CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // do something
                    validateAndSearch()
                }
                override fun onFinish() {
                    // do something
                }
            }
            timer.start()
        }
    }

    private fun validateAndSearch() {
        val searchQuery = "Trees"
        if (searchQuery.isEmpty()) {
            searchEditText.error = "Please type something to search"
            return
        }
        searchData(searchQuery)
    }

    private fun searchData(searchQuery: String) {
        coroutineJob = CoroutineScope(Dispatchers.IO).launch {
            val response = apiService?.getResult(searchQuery)

            withContext(Dispatchers.Main) {
                if (response?.isSuccessful == true) {
                    searchResultTextView.text = response.body().toString()
                }
            }
        }
    }

    override fun onPause() {
        coroutineJob?.cancel()
        super.onPause()
    }
}