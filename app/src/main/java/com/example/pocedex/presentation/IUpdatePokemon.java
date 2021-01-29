package com.example.pocedex.presentation;

import com.example.pocedex.data.Pokemon;

import java.util.List;

public interface IUpdatePokemon {

    void refresh(List<Pokemon> pokemons);

    void repeat();

}
