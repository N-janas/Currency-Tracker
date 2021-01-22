package com.example.currency_tracker.Model.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Class representing Database entity.
 *
 * @param rates currency_name: rate *(with respect to [base])*.
 * @param base currency against which rates will be calculated.
 * @param date the date of collecting data.
 */
@Entity(tableName = "currency_table")
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val idCurrency: Int,
    // There are a lot of currencies so instead of creating individual
    // fields, lets put a map string->double
    val rates: Map<String, Double> = mapOf(),
    // Name of base currency
    val base: String = "",
    // Date when the data was up-to-date
    val date: Date = Date(0)
)
