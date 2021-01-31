package com.example.currency_tracker.Model.Repositories

import androidx.lifecycle.LiveData
import com.example.currency_tracker.Model.Entities.Currency
import com.example.currency_tracker.Model.API.APIService
import com.example.currency_tracker.Model.CurrencyDao
import com.example.currency_tracker.Model.Entities.ResponseEntity
import com.example.currency_tracker.Model.ProjectDatabase
import retrofit2.awaitResponse
import java.util.*

class CurrencyRepository(private val currencyDao: CurrencyDao)
{
    suspend fun add(currency: Currency)
    {
        currencyDao.insertCurrency(currency)
    }

    suspend fun selectAll(): List<Currency>
    {
        return currencyDao.allCurrencies()
    }

    /**
     * Insert all objects in list as
     * new Currencies.
     */
    suspend fun addAll(currencies: List<Currency>)
    {
        currencyDao.insertAllCurrencies(currencies)
    }

    /**
     * Function is created for gathering **sorted** base Currency with every rate within
     * interval of specified time ([startAt], [endAt]).
     * @return list of one type of Currency specified by [base], with every other corresponding
     * rate. Data is gathered from specified interval of time.
     */
    fun getListOfCurrency(base: String, startAt: Date, endAt: Date): LiveData<List<Currency>> =
        currencyDao.getListOfCurrency(base, startAt, endAt)


    /**
     * Function checks is any record is already in table in database.
     * @return true if table is empty, otherwise false.
     */
    fun tableIsEmpty(): Boolean
    {
        return currencyDao.getOneElement().isEmpty()
    }

    /**
     * Function created for checking latest record date.
     * @return latest date of record in table in Date object.
     */
    fun getLatestDate(): Date
    {
        return currencyDao.getLatestDate()
    }

    // DEBUG functions
    /**
     * Debug function
     * @return list of all unique dates present in database.
     */
    suspend fun debugGetDatesInDb(): List<Date>
    {
        return currencyDao.debugGetDatesInDb()
    }

    /**
     * Debug function (created cuz selectAll doesn't fit in Logcat)
     * @return list of all currencies based on date.
     */
    suspend fun debugGetCurrenciesByDate(date: Date): List<Currency>
    {
        return currencyDao.debugGetCurrenciesByDate(date)
    }
}