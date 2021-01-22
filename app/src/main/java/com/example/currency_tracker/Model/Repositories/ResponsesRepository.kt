package com.example.currency_tracker.Model.Repositories

import android.util.Log
import com.example.currency_tracker.Model.API.APIService
import com.example.currency_tracker.Model.Entities.Currency
import com.example.currency_tracker.Model.Entities.ResponseEntity
import retrofit2.awaitResponse
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

class ResponsesRepository {

    companion object{

        /**
         * Function responsible for converting
         * response data to list of Currency objects that
         * can be placed in database.
         */
        fun convertToCurrency(response: ResponseEntity): List<Currency>{
            val convertedCurrencies = mutableListOf<Currency>()

            // Prepare date format
            val dateFormat: SimpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            // For every date with rates in response, make Currency object
            for ((date, rates) in response.rates){
                convertedCurrencies.add(
                    Currency(
                        idCurrency = 0,
                        rates = rates,
                        base = response.base,
                        date = dateFormat.parse(date)!!
                    )
                )
            }
            return convertedCurrencies
        }

        /**
         * @return latest rate between
         * [base] and [symbol] or **0.0** if something went wrong.
         */
        suspend fun getLatestRateBetweenTwo(symbol: String, base: String): Double {
            return APIService.api.getLatestRateBetweenTwo(symbol, base)
                .awaitResponse().body()?.rates?.get(symbol) ?: 0.0
        }

        /**
         * @return historical rates between [base] and [symbols]
         * in specified interval of time or *empty* [ResponseEntity] object
         * if something went wrong.
         */
        suspend fun getHistoricalRatesForCurrency(
            startAt: String,
            endAt: String,
            symbols: String,
            base: String
        ): ResponseEntity{
            return APIService.api.getHistoricalRatesForCurrency(startAt, endAt, symbols, base)
                .awaitResponse().body() ?: ResponseEntity()
        }


    }
}