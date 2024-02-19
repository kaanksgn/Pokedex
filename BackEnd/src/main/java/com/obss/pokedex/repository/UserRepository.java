package com.obss.pokedex.repository;

import com.obss.pokedex.model.Pokemon;
import com.obss.pokedex.model.Role;
import com.obss.pokedex.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findUsersByUsernameContainingIgnoreCase(String username, Pageable pageable);

    List<User> findUsersByUsernameContainingIgnoreCase(String username);

    @Query("select u from User u where  (?1 is null or u.id = ?1 ) and  (?2 is null or u.name like ?2 ) and (?3 is null or u.surname like ?3) and (?4 is null or u.username like ?4)")
    List<User> findByNameLikeIgnoreCaseOrTypeLikeIgnoreCase(Long id, String name, String surname, String username);

    @Transactional
    @Modifying
    @Query("update User u set u.name = ?1, u.surname = ?2, u.username = ?3, u.email = ?4 where u.id = ?5")
    int updateNameAndSurnameAndUsernameAndEmailById(String name, String surname, String username, String email, Long id);


}
