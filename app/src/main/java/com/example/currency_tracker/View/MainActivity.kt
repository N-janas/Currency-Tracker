package com.example.currency_tracker.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.example.currency_tracker.Model.MyWorker
import com.example.currency_tracker.R
import kotlinx.android.synthetic.main.fragment_welcome.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create constraints that will make internet connection required
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Setting up periodic work schedule
        val checkRatesRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<MyWorker>(24, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "apiCallRequest",
            ExistingPeriodicWorkPolicy.KEEP,
            checkRatesRequest
        )
    }
}