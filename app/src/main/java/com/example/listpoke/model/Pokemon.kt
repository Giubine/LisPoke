package com.example.listpoke.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val types: List<PokemonType>,
    val weight: Int,
    val height: Int,
    val name: String,
    val sprites: PokemonSprite,
) : Parcelable

 object pokemon {
    var DIFF_CALLBACK: DiffUtil.ItemCallback<Pokemon> =
        object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.id == newItem.id
            }
        }
}