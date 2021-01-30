package com.example.currency_tracker.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*
import com.example.currency_tracker.Model.Entities.Currency

@Dao
interface CurrencyDao {

    @Insert
    suspend fun insertCurrency(currency: Currency)

    // Second option was doing it by transaction
    // @Transaction
    // fun insertAllCurrencies(currencies: List<Currency>) = currencies.forEach {insertCurrency(it)}
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertAllCurrencies(currencies: List<Currency>)

    @Query("SELECT * FROM currency_table ORDER BY date")
    suspend fun allCurrencies(): List<Currency> // TODO(Tutaj chyba Live Data !!!)

    // Get sorted rates for base
    @Query("SELECT * FROM currency_table WHERE base = :base AND date BETWEEN :startAt AND :endAt ORDER BY date")
    fun getListOfCurrency(base: String, startAt: Date, endAt: Date): List<Currency> // TODO(Tutaj  chyba Live Data !!!)

    // Check if table is empty
    @Query("SELECT * FROM currency_table LIMIT 1")
    fun getOneElement(): List<Currency>

    // Get latest date from record within table
    @Query("SELECT MAX(date) FROM currency_table")
    fun getLatestDate(): Date

    // Debug -- Check dates in database
    @Query("SELECT DISTINCT date from currency_table")
    suspend fun debugGetDatesInDb(): List<Date>

    // Debug -- Check records by date in database
    @Query("SELECT * FROM currency_table WHERE date = :date")
    suspend fun debugGetCurrenciesByDate(date: Date): List<Currency>
}