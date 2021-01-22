package com.example.currency_tracker.Model

import androidx.room.TypeConverter
import com.example.currency_tracker.Model.Entities.Currency
import com.squareup.moshi.*
import java.util.*
import kotlin.reflect.typeOf

class Converters {
    @TypeConverter
    fun  fromString(value: String): Map<String, Double>{
        val moshi = Moshi.Builder().build()
        // Creating type of our map for JsonAdapter
        val type = Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            Double::class.javaObjectType
        )
        val adapter: JsonAdapter<Map<String, Double>> = moshi.adapter(type)

        return adapter.fromJson(value)!!
    }

    @TypeConverter
    fun fromHashMap(map: Map<String, Double>): String{
        val moshi = Moshi.Builder().build()
        // Creating type of our map for JsonAdapter
        val type = Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            Double::class.javaObjectType
        )
        val adapter: JsonAdapter<Map<String, Double>> = moshi.adapter(type)

        return adapter.toJson(map)
    }

    @TypeConverter
    fun fromTimeStamp(value: Long?): Date?{
        return  value?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long?{
        return date?.time?.toLong()
    }
}