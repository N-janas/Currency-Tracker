package com.example.currency_tracker.Model.Entities

import androidx.room.Entity

@Entity(primaryKeys = ["currencyBaseId", "currencySymbolId"])
data class Favourites(var currencyBaseId: Int, var currencySymbolId: Int)
