package com.example.listpoke.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.listpoke.database.PokemonEntity
import com.example.listpoke.repository.PokemonRepository

class PokemonFavoriteListActivityViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    fun loadOnlyFavorites(): LiveData<List<PokemonEntity>?> {
        return repository.getFavoritesPokemons()
    }

}