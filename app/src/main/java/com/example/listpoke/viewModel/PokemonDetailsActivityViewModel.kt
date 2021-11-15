package com.example.listpoke.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listpoke.database.PokemonEntity
import com.example.listpoke.repository.PokemonRepository
import com.example.listpoke.repository.Resource
import kotlinx.coroutines.launch

class PokemonDetailsActivityViewModel(
    private val repository: PokemonRepository
) : ViewModel() {
    private val pokemonLiveData = MediatorLiveData<Resource<PokemonEntity?>>()

    fun getPokemonById(pokemonId: Int): LiveData<Resource<PokemonEntity?>> {

        pokemonLiveData.addSource(repository.getPokemonByIdOnDatabase(pokemonId)) {
            if (it != null) {
                pokemonLiveData.value = Resource(data = it)
            } else {
                viewModelScope.launch {
                    pokemonLiveData.value = repository.getPokemonByIdOnApi(pokemonId).value
                }
            }
        }
        return pokemonLiveData
    }

    fun changeFavorite(pokemonReferente: PokemonEntity) {
        viewModelScope.launch {
            repository.changeFavorite(pokemonReferente)
        }
    }

}