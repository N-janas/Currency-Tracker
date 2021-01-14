package com.example.currency_tracker.Model.Repositories

import com.example.currency_tracker.Model.Entities.Currency
import com.example.currency_tracker.Model.API.APIService
import retrofit2.awaitResponse
import java.util.*

class CurrencyRepository {

    companion object{
        /**
         * @return Euro with corresponding (to 33 currencies) rates,
         * rates are collected from current date or *empty* [Currency] object
         * if something went wrong.
         */
        suspend fun getLatestEuro(): Currency{
            return APIService.api.getLatestEuro().awaitResponse().body() ?: Currency()
        }

        /**
         * @return latest rate between
         * [base] and [symbol] or *empty* [Currency] object if something went wrong.
         */
        suspend fun getLatestRateBetweenTwo(symbol: String, base: String): Currency{
            return APIService.api.getLatestRateBetweenTwo(symbol, base)
                .awaitResponse().body() ?: Currency()
        }

        /**
         * @return list of rates between [base] and [symbol]
         * in specified interval of time or *empty* lisfOf<[Currency]> object
         * if something went wrong.
         */
        suspend fun getHistoricalRatesBetweenTwo(
            startAt: String,
            endAt: String,
            symbol: String,
            base: String
        ): List<Currency>{
            return APIService.api.getHistoricalRatesBetweenTwo(startAt, endAt, symbol, base)
                .awaitResponse().body() ?: listOf()
        }
    }
}