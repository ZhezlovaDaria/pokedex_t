package com.example.pocedex.presentation

import com.example.pocedex.data.Pokemon

internal interface IUpdatePokemon {

    fun refresh(pokemons: List<Pokemon>)

    fun repeat()

}