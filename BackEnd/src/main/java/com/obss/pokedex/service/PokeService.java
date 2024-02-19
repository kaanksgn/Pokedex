package com.obss.pokedex.service;

import com.obss.pokedex.model.Pokemon;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PokeService {

    List<Pokemon> findPokemons(String name, Integer page, Integer size);

    Pokemon findById(Long id);

    Pokemon findByType(String type);

    Pokemon createNewPokemon(Pokemon pokemon);

    List<Pokemon> findAll();

    void createPokemon();

    public void deletePokemon(Long pokeId);

    List<Pokemon> listPokemonsBy(@Nullable String name, @Nullable String type);

    int updatePokemon(String name, String type, String description, Long id);

    List<Pokemon> findWishlistedPokemons(Long id);

    List<Pokemon> findCatchlistedPokemons(Long id);
}

