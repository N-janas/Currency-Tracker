package com.example.currency_tracker.Model.API

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

const val CURRENCY_API_URL = "https://api.exchangeratesapi.io/"

object APIService {

    // Create moshi converter to work with Kotlin, using KotlinJsonAdapterFactory
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Create retrofit object passing web service and converter
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(CURRENCY_API_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // Creates a public Retrofit service from CurrencyAPI Interface
    // It is public by object APIService, because call is expensive
    // and app only needs one Retrofit
    val api: CurrencyAPI by lazy {
        retrofit.create(CurrencyAPI::class.java)
    }
}