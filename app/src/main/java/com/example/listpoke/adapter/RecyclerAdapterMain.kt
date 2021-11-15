package com.example.listpoke.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.listpoke.R
import com.example.listpoke.database.PokemonEntity
import com.example.listpoke.util.changeTypeColor
import com.squareup.picasso.Picasso
import java.util.*

class RecyclerAdapterMain(
    private val context: Context,
    private val pokemonsList: MutableList<PokemonEntity> = mutableListOf<PokemonEntity>(),
    var onItemClicked: (pokemon: PokemonEntity) -> Unit = {}
) : RecyclerView.Adapter<RecyclerAdapterMain.ViewHolder>() {

    private val fullPokemonList = mutableListOf<PokemonEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val createdView = LayoutInflater.from(context)
            .inflate(R.layout.card_pokemon, parent, false)
        return ViewHolder(createdView)
    }

    override fun getItemCount(): Int = pokemonsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonsList[position]
        holder.linkPokemonDetails(pokemon)
    }

    fun update(data: List<PokemonEntity>) {
        data.forEach {
            if (!pokemonsList.contains(it)) {
                pokemonsList.add(it)
                notifyItemInserted(pokemonsList.size)
            }
            if (!fullPokemonList.contains(it)) {
                fullPokemonList.add(it)
            }
        }
    }

    fun getFilter(): Filter = searchedFilter

    private val searchedFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<PokemonEntity> = mutableListOf()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(fullPokemonList)
            } else {
                val filterPattern = constraint.toString().lowercase().trim()
                for (item in fullPokemonList) {

                    if (item.name.lowercase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                    if (item.id.toString().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            pokemonsList.clear()
            pokemonsList.addAll(results?.values as List<PokemonEntity>)
            notifyDataSetChanged()
        }
    }

    fun add(pokemon: PokemonEntity) {
        val position = itemCount

        if (!pokemonsList.contains(pokemon)) {
            pokemonsList.add(position, pokemon)
            notifyItemInserted(position)
        }
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var pokemon: PokemonEntity

        init {
            itemView.setOnClickListener {
                if (::pokemon.isInitialized) {
                    onItemClicked(pokemon)
                }
            }
        }

        fun linkPokemonDetails(
            pokemon: PokemonEntity
        ) {
            this.pokemon = pokemon
            when {
                pokemon.id < 10 -> {
                    "#00${pokemon.id}".also {
                        itemView.
                    }
                }
                pokemon.id < 100 -> {
                    "#0${pokemon.id}".also { itemView.txtPokemonNumber.text = it }
                }
                else -> {
                    "#${pokemon.id}".also { itemView.numero_pokemon.text = it }
                }
            }
            itemView.nome_pokemon.text = pokemon.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            Picasso.get()
                .load(pokemon.image)
                .error(R.drawable.who_is_the_pokemon)
                .into(itemView.imgCall)

            val typeName = pokemon.type1
            itemView.poke_type_1.text = typeName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            val unwrappedDrawable = itemView.cvPokemon.background
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable)
            changeTypeColor(typeName, wrappedDrawable)

            if (pokemon.type2 != null) {
                itemView.poke_type_2.visibility = View.VISIBLE
                val typeName2 = pokemon.type2
                itemView.poke_type_2.text = typeName2.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }

            } else {
                itemView.poke_type_2.visibility = View.GONE
            }
        }
    }
}