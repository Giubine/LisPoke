package com.example.listpoke.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonTypeConverter {

    @TypeConverter
    fun toPokemonTypeList(stringValues : String) : List<enumTypes>{
        val type = object : TypeToken<List<enumTypes>>(){}.type
        return Gson().fromJson(stringValues, type)
    }

    @TypeConverter
    fun fromPokemonTypeList( types :List<enumTypes>) : String{
        return Gson().toJson(types)
    }
}