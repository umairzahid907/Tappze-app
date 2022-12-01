package com.example.tappze.data.model

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonElement

class HashMapConverter {

    @TypeConverter
    fun toHashMap(value: String): Map<String, String> =
        Gson().fromJson(value, object : TypeToken<Map<String, String>>() {}.type)

    @TypeConverter
    fun fromHashMap(value: Map<String, String>): String =
        Gson().toJson(value)

}