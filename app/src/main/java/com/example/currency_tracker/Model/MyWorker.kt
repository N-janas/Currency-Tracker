package com.example.currency_tracker.Model

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.currency_tracker.Model.Entities.ResponseEntity
import com.example.currency_tracker.Model.Repositories.CurrencyRepository
import com.example.currency_tracker.Model.Repositories.ResponsesRepository
import com.example.currency_tracker.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val UPDATE_CAP: Long = 7L
const val WEEK_IN_MILLISECONDS: Long = 1000*60*60*24*7
const val DAY_IN_MILLISECONDS: Long = 1000*60*60*24
const val FIFTEEN_DAYS_IN_MILLISECONDS: Long = 1000*60*60*24*15

class MyWorker(appContext: Context, workerParams: WorkerParameters):
        CoroutineWorker(appContext, workerParams){

    private var againToday: Boolean = false

    // Repository for inserting to database
    private val currencyRepository: CurrencyRepository =
        CurrencyRepository(ProjectDatabase.getDatabase(appContext).currencyDao())

    // Calculate difference between today and specified date
    // and return that difference in days
    private fun getDifferenceInDates(date: Date): Long{
        val diff: Long = Date().time - date.time
        return TimeUnit.MILLISECONDS.toDays(diff)
    }

    override suspend fun doWork(): Result {
        var startAt: String
        var endAt: String

        // If database is empty, then fill it
        if (currencyRepository.tableIsEmpty()){
            // Get just today data
            // Option 1: Last 30 days
            startAt =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                    // Subtract 2 times 15 = 30 (cuz overflow)
                    Date(Date().time - FIFTEEN_DAYS_IN_MILLISECONDS
                            - FIFTEEN_DAYS_IN_MILLISECONDS)
                )

            endAt =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            // Option 2: Current day only
//            startAt =
//                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
//
//            endAt =
//                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        } else{
            // Otherwise try to update it
            val latestDate = currencyRepository.getLatestDate()

            // If worker is trying to gather data again today then skip this request
            if (DateUtils.isToday(latestDate.time)){
                againToday = true
                Log.d("workerLog", "You tried to update again today's data in DB")
            }

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
        val supportedCurrencies = applicationContext.resources
            .getStringArray(R.array.supported_currencies)

        Log.d("workerLog", "Supported currencies : " + supportedCurrencies.joinToString(","))
        // If not trying to do same work again today
        if (!againToday){
            // Collect rates for every supported currency
            for (base in supportedCurrencies){
                // Get response for current base
                val response = ResponsesRepository.getHistoricalRatesForCurrency(
                    startAt,
                    endAt,
                    supportedCurrencies.filter { it != base }.joinToString(separator = ","),
                    base
                )

                // If there was no currency updates then do stop operations
                if (response.rates.isEmpty()){
                    Log.d("workerLog", "There was no update in API")
                    break
                }

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
        }
        Log.d("workerLog", "Done working")
        againToday = false;
        return Result.success()
    }
}