package com.example.currency_tracker.Model.Repositories

import com.example.currency_tracker.Model.Entities.Currency
import com.example.currency_tracker.Model.API.APIService
import retrofit2.awaitResponse
import java.util.*

class CurrencyRepository {

    companion object{

        suspend fun getLatestEuro(): Currency{
            return APIService.api.getLatestEuro().awaitResponse().body() ?: Currency()
        }
    }
}