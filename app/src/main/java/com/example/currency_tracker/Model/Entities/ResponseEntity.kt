package com.example.currency_tracker.Model.Entities

import com.squareup.moshi.Json

/**
 * Class representing API response.
 *
 * @param rates Map consisting of date: map<Currency, Rate>
 * @param base currency against which rates will be calculated
 * @param startAt first day when rates weren't downloaded
 * @param endAt most recent date
 */
data class ResponseEntity (
    // {rates: {"date : {"currency": rate, "currency": rate, ...}, ....}}
    val rates: Map<String, Map<String, Double>> = mapOf(Pair("", mapOf())),
    // First day when rates weren't downloaded
    @Json(name = "start_at")
    val startAt: String = "",
    // Name of base currency
    val base: String = "",
    // Most recent date
    @Json(name = "end_at")
    val endAt: String = ""
)
