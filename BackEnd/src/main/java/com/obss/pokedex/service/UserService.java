package com.obss.pokedex.service;

import com.obss.pokedex.model.Pokemon;
import com.obss.pokedex.model.Role;
import com.obss.pokedex.model.User;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> findUsers(String username, Integer page, Integer size);


    User findById(Long id);

    User createNewUser(User user);

    User createNewUser(User user, String password);

    void deleteUser(Long userId);

    User createNewAdmin(User user, String password);

    Optional<User> findByUsername(String username);

    List<User> listUsersBy(@Nullable Long id, @Nullable String name, @Nullable String surname, @Nullable String username);

    void checkAndCreateUser();

    void checkAndCreateAdminUser();

    void removeFromWishlist(Long pokeId);

    public List<User> findAll();

    void addPokemonToWishList(Long userId, Long pokeId);

    void removePokemonFromWishList(Long userId, Long pokeId);

    void removePokemonFromCatchList(Long userId, Long pokeId);

    void addPokemonToCatchList(Long userId, Long pokeId);

    int updateUser(String name, String surname, String username, String email, Long id);

}
