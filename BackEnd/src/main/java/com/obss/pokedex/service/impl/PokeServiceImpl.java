package com.obss.pokedex.service.impl;

import com.obss.pokedex.exception.PokeNotFoundException;
import com.obss.pokedex.model.Pokemon;
import com.obss.pokedex.repository.PokeRepository;
import com.obss.pokedex.repository.UserRepository;
import com.obss.pokedex.service.PokeService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokeServiceImpl implements PokeService {
    private final PokeRepository pokeRepository;
    private final UserRepository userRepository;

    @Override
    public List<Pokemon> findPokemons(String name, Integer page, Integer size) {
        if (StringUtils.isBlank(name)) {
            if (Objects.nonNull(page) && Objects.nonNull(size)) {
                return pokeRepository.findAll(PageRequest.of(page, size)).stream().toList();
            }
            return pokeRepository.findAll();
        }
        if (Objects.nonNull(page) && Objects.nonNull(size)) {
            return pokeRepository.findPokemonsByNameContainingIgnoreCase(name, PageRequest.of(page, size)).stream().toList();
        }


        return pokeRepository.findPokemonsByNameContainingIgnoreCase(name);
    }

    @Override
    public Pokemon findById(Long id) {
        Objects.requireNonNull(id, "Pokemon name is required");

        return pokeRepository.findById(id).orElseThrow(PokeNotFoundException::new);
    }

    @Override
    public Pokemon findByType(String type) {
        Objects.requireNonNull(type, "Pokemon type is required");

        return pokeRepository.findByType(type).orElseThrow(PokeNotFoundException::new);
    }

    @Override
    public Pokemon createNewPokemon(Pokemon pokemon) {
        Objects.requireNonNull(pokemon, "Pokemon is missing");
        pokeRepository.save(pokemon);
        return pokemon;
    }

    @Override
    public List<Pokemon> findAll() {
        return pokeRepository.findAll();
    }

    @Override
    public void createPokemon() {
        pokeRepository.findPokemonByName("pikachu").orElseGet(() -> {
            final Pokemon poke = Pokemon
                    .builder()
                    .name("pikachu")
                    .type("PokeType")
                    .description("PokeDescription")
                    .build();
            return createNewPokemon(poke);
        });
    }

    @Override
    public List<Pokemon> listPokemonsBy(String name, String type) {
        return pokeRepository.findByNameLikeIgnoreCaseOrTypeLikeIgnoreCase(name, type);
    }

    public void deletePokemon(Long pokeId) {
        pokeRepository.deleteById(pokeId);

    }

    @Override
    public List<Pokemon> findWishlistedPokemons(Long id) {
        return pokeRepository.findByWishlistedUsers_IdOrderByIdAsc(id);
    }

    @Override
    public List<Pokemon> findCatchlistedPokemons(Long id) {
        return pokeRepository.findByCatchlistedUsers_IdOrderByIdAsc(id);
    }

    @Override
    public int updatePokemon(String name, String type, String description, Long id) {
        return pokeRepository.updateNameAndTypeAndDescriptionById(name, type, description, id);
    }
}
