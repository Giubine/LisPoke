package com.example.listpoke.model

data class PokemonModel(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)