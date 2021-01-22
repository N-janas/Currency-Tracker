package com.example.currency_tracker.Model.Repositories

import androidx.lifecycle.LiveData
import com.example.currency_tracker.Model.Entities.Currency
import com.example.currency_tracker.Model.API.APIService
import com.example.currency_tracker.Model.CurrencyDao
import com.example.currency_tracker.Model.Entities.ResponseEntity
import com.example.currency_tracker.Model.ProjectDatabase
import retrofit2.awaitResponse
import java.util.*

class CurrencyRepository(private val currencyDao: CurrencyDao) {

    suspend fun add(currency: Currency){
        currencyDao.insertCurrency(currency)
    }

    suspend fun selectAll(): List<Currency>{
        return currencyDao.allCurrencies()
    }

    /**
     * Insert all objects in list as
     * new Currencies.
     */
    suspend fun addAll(currencies: List<Currency>){
        currencyDao.insertAllCurrencies(currencies)
    }

    /**
     * Function is created for gathering **unsorted** base Currency with every rate within
     * interval of specified time ([startAt], [endAt]).
     * @return list of one type of Currency specified by [base], with every other corresponding
     * rate. Data is gathered from specified interval of time.
     */
    suspend fun getListOfCurrency(base: String, startAt: Date, endAt: Date): List<Currency>{
        // If startAt and endAt will be strings, then here should be the conversion
        return currencyDao.getListOfCurrency(base, startAt, endAt)
    }
}