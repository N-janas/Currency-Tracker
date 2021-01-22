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

class MyWorker(appContext: Context, workerParams: WorkerParameters):
        CoroutineWorker(appContext, workerParams){

    // Repository for inserting to database
    val currencyRepository: CurrencyRepository =
        CurrencyRepository(ProjectDatabase.getDatabase(appContext).currencyDao())

    override suspend fun doWork(): Result {
        // Get date of last time when date was gathered (Database last item Date)
        // TODO (Zrobić cap jeśli odległość dni bez sprawdzania databseDate-today > cap )
        // TODO (to pobrać od Today do today-cap dni, zeby uniknąć, Jak pusty DB to bez  capa)
        val startAt: String = "2021-01-17"
//            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        // Get current Date
        val endAt: String =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

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
        val formatTMP = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        Log.d("dateTest",currencyRepository.getListOfCurrency(
            "EUR",
            formatTMP.parse("2021-01-17")!!,
            formatTMP.parse("2021-01-22")!!
        ).toString())
        Log.d("Result", currencyRepository.selectAll().toString())
        // DEBUG -----------

        return Result.success()
    }
}