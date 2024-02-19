package com.obss.pokedex.repository;

import com.obss.pokedex.model.Pokemon;
import com.obss.pokedex.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Repository
public interface PokeRepository extends JpaRepository<Pokemon, Long> {

    Optional<Pokemon> findPokemonByName(String name);

    Optional<Pokemon> findByType(String type);

    List<Pokemon> findPokemonsByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Pokemon> findPokemonsByNameContainingIgnoreCase(String name);

    List<Pokemon> findPokemonsByTypeContainingIgnoreCase(String type, Pageable pageable);

    List<Pokemon> findPokemonsByTypeContainingIgnoreCase(String type);

   /* @Query("select p from Pokemon p where upper(p.name) like upper(?1) and upper(p.type) like upper(?2)")
    List<Pokemon> findByNameLikeIgnoreCaseAndTypeLikeIgnoreCase(@Nullable String name, @Nullable String type);*/

    @Query("select p from Pokemon p where (?1 is null or p.name like ?1 ) and (?2 is null or p.type like ?2)")
    List<Pokemon> findByNameLikeIgnoreCaseOrTypeLikeIgnoreCase(String name, String type);

    @Transactional
    @Modifying
    @Query("update Pokemon p set p.name = ?1, p.type = ?2, p.description = ?3 where p.id = ?4")
    int updateNameAndTypeAndDescriptionById(String name, String type, String description, Long id);

    @Query("""
            select p from Pokemon p inner join p.wishlistedUsers wishlistedUsers
            where wishlistedUsers.id = ?1
            order by p.id""")
    List<Pokemon> findByWishlistedUsers_IdOrderByIdAsc(Long id);

    @Query("""
            select p from Pokemon p inner join p.catchlistedUsers catchlistedUsers
            where catchlistedUsers.id = ?1
            order by p.id""")
    List<Pokemon> findByCatchlistedUsers_IdOrderByIdAsc(Long id);


}
