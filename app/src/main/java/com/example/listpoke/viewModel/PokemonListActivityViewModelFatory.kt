package com.example.listpoke.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.listpoke.repository.PokemonRepository

abstract class PokemonListActivityViewModelFactory(
    private val repository: PokemonRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonListActivityViewModel(repository) as T
    }
}