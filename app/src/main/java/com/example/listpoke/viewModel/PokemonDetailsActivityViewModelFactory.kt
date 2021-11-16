package com.example.listpoke.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.listpoke.repository.PokemonRepository

 class PokemonDetailsActivityViewModelFactory(
    private val repository: PokemonRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PokemonDetailsActivityViewModel(repository) as T
    }

}