package com.example.listpoke.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listpoke.R
import com.example.listpoke.adapter.RecyclerAdapterMain
import com.example.listpoke.api.PokemonWebClient
import com.example.listpoke.database.AppDatabase
import com.example.listpoke.database.PokemonEntity
import com.example.listpoke.repository.PokemonRepository
import com.example.listpoke.repository.Resource
import com.example.listpoke.util.POKEMON_CHAVE
import com.example.listpoke.viewModel.PokemonListActivityViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.example.listpoke.database.PokemonDao as DatabasePokemonDao
import com.example.listpoke.viewModel.PokemonListActivityViewModelFactory as PokemonListActivityViewModelFactory1

const val LOAD_ERROR = "Erro para carregar lista"

class PokemonListActivity : AppCompatActivity() {

    private val adapter by lazy {
        RecyclerAdapterMain(context = this)
    }
    private val layoutManager by lazy {
        GridLayoutManager(this, 2)
    }
    private val viewModel by lazy {
        val repository = PokemonRepository(AppDatabase.getInstance(this).pokemonDAO)
        val factory = PokemonListActivityViewModelFactory1 (repository)

        ViewModelProvider(this, factory)
            .get(PokemonListActivityViewModel::class.java)
    }
    private var isSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startConfiguration()

        Picasso.setSingletonInstance(
            Picasso.Builder(this) // additional settings
                .build()
        )
    }

    private fun startConfiguration() {
        recycleViewConfigurations()
        loadPokemons()
        endlessScroll()
        menuClickListener()
    }

    val materialAppBar: Any?= null

    private fun menuClickListener() {
        materialAppBar.setOnMenuClickListener {
            when (it.itemId) {
                R.id.favorite_list -> {
                    val intent = Intent(this, PokemonFavoriteListActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.search_menu -> {
                    searchEngine()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun searchEngine() {
        val menuItem = materialAppBar.menu.findItem(R.id.search_menu)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = "Pesquisar Pokemon"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (adapter.itemCount == 0) {
                    viewModel.getOnPokemonByNameOrId(newText)
                        .observe(this@PokemonListActivity, { pokemonList ->
                            handleUpdateAdapter(pokemonList)
                        })
                }
                adapter.getFilter().filter(newText)
                return true
            }
        })

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                isSearching = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                loadPokemons()
                isSearching = false
                return true
            }

        })

    }

    private fun endlessScroll() {
        recycleView_main.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val listSize = adapter.itemCount
                if (lastItem >= listSize - 6 && !isSearching) {
                    loadMorePokemonsViewModel(listSize)
                }
            }
        }
        )

    }

    private fun loadMorePokemonsViewModel(listSize: Int) {
        viewModel.loadMorePokemons(listSize + 2)
            .observe(this@PokemonListActivity, { resource ->
                resource?.data?.let { pokemon ->
                    adapter.add(pokemon)
                }
                resource?.error?.let {
                    Snackbar.make(recycleView_main, LOAD_ERROR, Snackbar.LENGTH_LONG).show()
                }
            })
    }

    private fun recycleViewConfigurations() {
        recycleView_main.layoutManager = layoutManager
        recycleView_main.adapter = adapter
        adapter.onItemClicked = ::openPokemonDetails
    }

    private fun openPokemonDetails(pokemonId: PokemonEntity) {
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra(POKEMON_CHAVE, pokemonId.id)
        startActivity(intent)
    }

    private fun loadPokemons() {
        viewModel.findAll().observe(this, { pokemonList ->
            handleUpdateAdapter(pokemonList)
        })
    }

    private fun handleUpdateAdapter(pokemonList: Resource<List<PokemonEntity>?>) {
        if (!pokemonList.data.isNullOrEmpty()) {
            adapter.update(data = pokemonList.data)
        } else if (!pokemonList.error.isNullOrEmpty()) {
            Snackbar.make(recycleView_main, pokemonList.error, Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(recycleView_main, LOAD_ERROR, Snackbar.LENGTH_LONG).show()
        }
    }


}

