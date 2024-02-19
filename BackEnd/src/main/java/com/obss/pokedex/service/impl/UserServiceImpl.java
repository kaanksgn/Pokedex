package com.obss.pokedex.service.impl;

import com.obss.pokedex.common.Constants;
import com.obss.pokedex.common.Constants.Roles;
import com.obss.pokedex.dto.BaseResponse;
import com.obss.pokedex.exception.UserNotFoundException;
import com.obss.pokedex.model.Pokemon;
import com.obss.pokedex.model.Role;
import com.obss.pokedex.model.User;
import com.obss.pokedex.repository.PokeRepository;
import com.obss.pokedex.repository.UserRepository;
import com.obss.pokedex.security.UserAuthDetails;
import com.obss.pokedex.service.PokeService;
import com.obss.pokedex.service.RoleService;
import com.obss.pokedex.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PokeService pokeService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public List<User> findUsers(String username, Integer page, Integer size) {
        if (StringUtils.isBlank(username)) {
            if (Objects.nonNull(page) && Objects.nonNull(size)) {
                return userRepository.findAll(PageRequest.of(page, size)).stream().toList();
            }
            return userRepository.findAll();
        }
        if (Objects.nonNull(page) && Objects.nonNull(size)) {
            return userRepository.findUsersByUsernameContainingIgnoreCase(username, PageRequest.of(page, size)).stream().toList();
        }


        return userRepository.findUsersByUsernameContainingIgnoreCase(username);
    }

    @Override
    public User findById(Long id) {
        Objects.requireNonNull(id, "User ID is required");
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> listUsersBy(Long id, String name, String surname, String username) {
        return userRepository.findByNameLikeIgnoreCaseOrTypeLikeIgnoreCase(id, name, surname, username);
    }

    @Transactional
    @Override
    public User createNewUser(User user) {
        Objects.requireNonNull(user, "User is missing");
        userRepository.save(user);
        return user;
    }

    @Override
    public int updateUser(String name, String surname, String username, String email, Long id) {
        return userRepository.updateNameAndSurnameAndUsernameAndEmailById(name, surname, username, email, id);
    }

    @Override
    public User createNewAdmin(User user, String password) {
        Objects.requireNonNull(user, "User is missing");
        user.setPassword(passwordEncoder.encode(password));
        Role role = roleService.findByName(Roles.ADMIN);


        user.setRoles(List.of(role));
        role.getUsers().add(user);


        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User createNewUser(User user, String password) {
        Objects.requireNonNull(user, "User is missing");
        user.setPassword(passwordEncoder.encode(password));
        Role role = roleService.findByName(Roles.USER);


        user.setRoles(List.of(role));
        role.getUsers().add(user);
        return userRepository.save(user);

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Transactional
    @Override
    public void checkAndCreateUser() {
        userRepository.findByUsername("user").orElseGet(() -> {

            Role role = roleService.findByName(Roles.USER);
            final User user = User
                    .builder()
                    .email("sysuser@user.com")
                    .name("User")
                    .surname("User")
                    .username("user")
                    .enabled(Boolean.TRUE)
                    .password(passwordEncoder.encode("Admin123"))
                    .roles(List.of(role))
                    .wishListedPokemons(Collections.emptyList())
                    .catchListedPokemons(Collections.emptyList())
                    .build();
            role.getUsers().add(user);

            return createNewUser(user);

        });
    }

    @Override
    public void removeFromWishlist(Long pokeId) {

    }

    @Transactional
    @Override
    public void checkAndCreateAdminUser() {
        userRepository.findByUsername("sysadmin").orElseGet(() -> {

            Role role = roleService.findByName(Constants.Roles.ADMIN);
            final User admin = User
                    .builder()
                    .email("sysadmin@admin.com")
                    .name("Admin")
                    .surname("Admin")
                    .username("sysadmin")
                    .enabled(Boolean.TRUE)
                    .password(passwordEncoder.encode("Admin123"))
                    .roles(List.of(role))

                    .build();
            role.getUsers().add(admin);

            return createNewUser(admin);

        });
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void addPokemonToWishList(Long userId, Long pokeId) {
        User updatedUser = userRepository.findById(userId).get();
        Pokemon pokemon = pokeService.findById(pokeId);

        updatedUser.getWishListedPokemons().add(pokemon);
        pokemon.getWishlistedUsers().add(updatedUser);
        userRepository.save(updatedUser);
    }

    @Override
    public void removePokemonFromWishList(Long userId, Long pokeId) {
        User updatedUser = userRepository.findById(userId).get();
        Pokemon pokemon = pokeService.findById(pokeId);

        updatedUser.getWishListedPokemons().remove(pokemon);
        pokemon.getWishlistedUsers().remove(updatedUser);
        userRepository.save(updatedUser);

//        userEdited.getWishListedPokemons().remove(pokeService.findById(pokeId));
//        pokeService.findById(pokeId).getWishlistedUsers().remove(userEdited);
//        userService.updateUser(userEdited.getName(), userEdited.getSurname(), userEdited.getUsername(), userEdited.getEmail(), userEdited.getId());
//        return new BaseResponse<>(202L, "Removed From Wishlist");
    }

    @Override
    public void removePokemonFromCatchList(Long userId, Long pokeId) {
        User updatedUser = userRepository.findById(userId).get();
        Pokemon pokemon = pokeService.findById(pokeId);

        updatedUser.getCatchListedPokemons().remove(pokemon);
        pokemon.getCatchlistedUsers().remove(updatedUser);
        userRepository.save(updatedUser);
    }

    @Override
    public void addPokemonToCatchList(Long userId, Long pokeId) {
        User updatedUser = userRepository.findById(userId).get();
        Pokemon pokemon = pokeService.findById(pokeId);

        updatedUser.getCatchListedPokemons().add(pokemon);
        pokemon.getCatchlistedUsers().add(updatedUser);
        userRepository.save(updatedUser);

    }
}
