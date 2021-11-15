package com.example.listpoke.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.listpoke.R
import com.example.listpoke.adapter.RecyclerAdapterMain
import com.example.listpoke.database.AppDatabase
import com.example.listpoke.database.PokemonEntity
import com.example.listpoke.repository.PokemonRepository
import com.example.listpoke.util.POKEMON_CHAVE
import com.example.listpoke.viewModel.PokemonFavoriteListActivityViewModel
import com.example.listpoke.viewModel.PokemonFavoriteListActivityViewModelFactory

class PokemonFavoriteListActivity : AppCompatActivity() {

    private val adapter by lazy {
        RecyclerAdapterMain(context = this)
    }
    private val gridLayoutManager by lazy {
        GridLayoutManager(this,2)
    }

    private val viewModel by lazy {
        val repository = PokemonRepository(AppDatabase.getInstance(this).pokemonDAO)
        val factory = PokemonFavoriteListActivityViewModelFactory (repository)
        ViewModelProvider(this, factory)
            .get(PokemonFavoriteListActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_favorite_list)
        startConfig()
        loadFavorites()


        materialAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun startConfig() {
        recycleView_main.layoutManager = gridLayoutManager
        recycleView_main.adapter = adapter
        adapter.onItemClicked = ::openPokemonDetails
    }

    private fun openPokemonDetails(pokemonId: PokemonEntity) {
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra(POKEMON_CHAVE, pokemonId.id)
        startActivity(intent)
    }

    private fun loadFavorites() {
        viewModel.loadOnlyFavorites().observe(this, {listPokemons ->
            listPokemons?.let{
                adapter.update(it)
            }
        })
    }

}