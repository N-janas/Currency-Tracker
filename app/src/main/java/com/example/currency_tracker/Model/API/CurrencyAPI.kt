package com.example.currency_tracker.Model.API

import com.example.currency_tracker.Model.Entities.Currency
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface CurrencyAPI {

    /**
     * @return Euro with corresponding (to 33 currencies) rates,
     * rates are collected from current date.
     */
    @GET("latest")
    fun getLatestEuro(): Call<Currency>

    /**
     * @return latest rate between
     * [base] and [symbol].
     */
    @GET("latest")
    fun getLatestRateBetweenTwo(
        @Query("symbols") symbol: String,
        @Query("base") base: String
    ): Call<Currency>

    /**
     * @return list of rates between [base] and [symbol]
     * in specified interval of time.
     */
    @GET("history")
    fun getHistoricalRatesBetweenTwo(
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String,
        @Query("symbols") symbol: String,
        @Query("base") base: String
    ): Call<List<Currency>>
}