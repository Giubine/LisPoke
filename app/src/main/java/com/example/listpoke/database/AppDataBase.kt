package com.example.listpoke.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

private const val DATABASE_NAME = "pokedex.db"

@Database(entities = [PokemonEntity::class], version = 6, exportSchema = false)
@TypeConverters(PokemonTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val pokemonDAO: PokemonDAO

    companion object {
        private lateinit var db: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (Companion::db.isInitialized) return db

            db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()

            return db
        }
    }

}
