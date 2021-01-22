package com.example.currency_tracker.Model.API

import com.example.currency_tracker.Model.Entities.Currency
import com.example.currency_tracker.Model.Entities.ResponseEntity
import com.example.currency_tracker.Model.Entities.ResponseLatestEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface CurrencyAPI {

    /**
     * @return latest rate between
     * [base] and [symbol].
     */
    @GET("latest")
    fun getLatestRateBetweenTwo(
        @Query("symbols") symbol: String,
        @Query("base") base: String
    ): Call<ResponseLatestEntity>

    /**
     * @return [ResponseEntity] object that contains
     * rates between [base] and [symbols]
     * in specified interval of time.
     */
    @GET("history")
    fun getHistoricalRatesForCurrency(
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String,
        @Query("symbols") symbols: String,
        @Query("base") base: String
    ): Call<ResponseEntity>
}