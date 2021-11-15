package com.example.listpoke.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonType (
    val slot : Int,
    val type : TypeInformations
) : Parcelable