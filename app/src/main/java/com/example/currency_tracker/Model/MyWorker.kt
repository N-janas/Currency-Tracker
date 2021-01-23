package com.example.currency_tracker.Model

import android.content.Context
import android.util.Log
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.currency_tracker.Model.Entities.ResponseEntity
import com.example.currency_tracker.Model.Repositories.CurrencyRepository
import com.example.currency_tracker.Model.Repositories.ResponsesRepository
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val UPDATE_CAP: Long = 7L
const val WEEK_IN_MILLISECONDS: Long = 1000*60*60*24*7
const val DAY_IN_MILLISECONDS: Long = 1000*60*60*24

class MyWorker(appContext: Context, workerParams: WorkerParameters):
        CoroutineWorker(appContext, workerParams){

    // Repository for inserting to database
    private val currencyRepository: CurrencyRepository =
        CurrencyRepository(ProjectDatabase.getDatabase(appContext).currencyDao())

    // Calculate difference between today and specified date
    // and return that difference in days
    fun getDifferenceInDates(date: Date): Long{
        val diff: Long = Date().time - date.time
        return TimeUnit.MILLISECONDS.toDays(diff)
    }

    override suspend fun doWork(): Result {
        var startAt: String
        var endAt: String

        // If database is empty, then fill it
        if (currencyRepository.tableIsEmpty()){
            // Get just today data
//            val startAt: String =
//                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
//
//            val endAt: String =
//                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            // DEBUG ---------------- fill DB
            startAt = "2021-01-15"
            endAt = "2021-01-19"
            // DEBUG ----------------
        } else{
            // Otherwise try to update it
            val latestDate = currencyRepository.getLatestDate()
            // If difference in days between today and latest date was too high
            if (getDifferenceInDates(latestDate) > UPDATE_CAP) {
                // then update only part of it specified by UPDATE_CAP (today - UPDATE_CAP)
                startAt =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                        Date(Date().time - WEEK_IN_MILLISECONDS)
                    )
                // Get current Date
                endAt =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            } else{
                // Get interval from latest (exclusive) to current
                startAt =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                        Date(latestDate.time + DAY_IN_MILLISECONDS)
                    )
                // Get current Date
                endAt =
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            }
        }

        // Get date of last time when date was gathered (Database last item Date)
        val supportedCurrencies = listOf("EUR", "PLN", "USD") //, "AUD", "TRY"

        // Collect rates for every supported currency
        for (base in supportedCurrencies){
            // Get response for current base
            val response = ResponsesRepository.getHistoricalRatesForCurrency(
                startAt,
                endAt,
                supportedCurrencies.filter { it != base }.joinToString(separator = ","),
                base
            )

            // Convert to Currency entities list for current processing base
            // and return a list based of dates. E.g:
            // [("EUR", "2020-01-01", ...), ("EUR", "2020-01-05", ...), ("EUR", "2020-01-15", ...)]
            val newBaseRecords = ResponsesRepository.convertToCurrency(response)

            // Add all Currencies based of date as new records
            currencyRepository.addAll(
                newBaseRecords
            )
            // Start processing new base...
        }
        // DEBUG -----------
//        val formatTMP = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//
//        Log.d("dateTest",currencyRepository.getListOfCurrency(
//            "EUR",
//            formatTMP.parse("2021-01-17")!!,
//            formatTMP.parse("2021-01-22")!!
//        ).toString())
//        Log.d("Result", currencyRepository.selectAll().toString())
        // DEBUG -----------

        return Result.success()
    }
}