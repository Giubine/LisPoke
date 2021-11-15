package com.example.listpoke.api

class PokemonWebClient (
    private val service: PokeApiService = RetroftInstance().pokeApiService
) {
    suspend fun findPokemonById(id: Int) = service.getPokemonById(id)
    suspend fun findPokemonByIdOrName(nameOrId: String) = service.getPokemonByNameOrId(nameOrId)
}