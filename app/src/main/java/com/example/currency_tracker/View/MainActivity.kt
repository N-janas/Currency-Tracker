package com.example.currency_tracker.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.example.currency_tracker.Model.MyWorker
import com.example.currency_tracker.R
import kotlinx.android.synthetic.main.fragment_welcome.*
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
//        val checkRatesRequest: WorkRequest =
//            PeriodicWorkRequestBuilder<MyWorker>(5, TimeUnit.MINUTES)
//                .setConstraints(constraints)
//                .setBackoffCriteria(
//                    BackoffPolicy.LINEAR,
//                    10,
//                    TimeUnit.SECONDS)
//                .build()


        //Zakomentowałem to bo mi wgle nie ruszało P.J
//        btnWelcomeToConversion.setOnClickListener {
//            val checkRatesRequest: WorkRequest =
//                OneTimeWorkRequestBuilder<MyWorker>()
//                    .setConstraints(constraints)
//                    .build()
//            // Enqueue scheduled work
//            WorkManager.getInstance(applicationContext).enqueue(checkRatesRequest)
//        }
    }
}