package com.example.currency_tracker.Model.Entities

data class Currency(
    // There are a lot of currencies so instead of creating individual
    // fields, lets put a map string->double
    val rates: Map<String, Double> = mapOf(),
    // Name of base currency
    val base: String = "",
    // Date when the data was up-to-date
    val date: String = ""
)
