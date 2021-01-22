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

    @Query("SELECT * FROM currency_table")
    fun allCurrencies(): List<Currency> // TODO(Tutaj Live Data !!!)


    @Query("SELECT * FROM currency_table WHERE base = :base AND date BETWEEN :startAt AND :endAt")
    fun getListOfCurrency(base: String, startAt: Date, endAt: Date): List<Currency> // TODO(Tutaj Live Data !!!)

}