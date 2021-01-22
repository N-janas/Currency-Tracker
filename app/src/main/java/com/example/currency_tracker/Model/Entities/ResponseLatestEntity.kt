package com.example.currency_tracker.Model.Entities

import androidx.room.PrimaryKey
import java.util.*

data class ResponseLatestEntity(
    // There are a lot of currencies so instead of creating individual
    // fields, lets put a map string->double
    val rates: Map<String, Double> = mapOf(Pair("none", 0.0)),
    // Name of base currency
    val base: String = "",
    // Date when the data was up-to-date
    val date: String = ""
)