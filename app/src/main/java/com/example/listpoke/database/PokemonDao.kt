package com.example.listpoke.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity> )

    @Query("SELECT * FROM pokemonentity ORDER BY id")
    fun findAll() : LiveData<List<PokemonEntity>?>

    @Query("SELECT * FROM pokemonentity WHERE id == :pokemonId")
    fun findById(pokemonId: Int) : LiveData<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pokemon: PokemonEntity)

    @Update
    suspend fun updatePokemon(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemonentity WHERE isFavorite == :valor")
    fun findOnlyFavorites(valor: Boolean): LiveData<List<PokemonEntity>?>

    @Query("SELECT * FROM pokemonentity WHERE name == :name")
    fun findByName(name: String) : LiveData<List<PokemonEntity>?>
}