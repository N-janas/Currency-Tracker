package com.example.currency_tracker.Model.API

import com.example.currency_tracker.Model.Entities.Currency
import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface CurrencyAPI {

    @GET("latest")
    fun getLatestEuro(): Call<Currency>

}