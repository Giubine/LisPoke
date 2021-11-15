package com.example.listpoke.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TypeInformations(
    val name : String,
    val url : String
) : Parcelable