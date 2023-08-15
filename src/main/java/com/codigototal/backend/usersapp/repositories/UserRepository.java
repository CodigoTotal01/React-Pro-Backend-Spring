package com.codigototal.backend.usersapp.repositories;

import com.codigototal.backend.usersapp.models.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    //Emeplando nombre del metodo en vez de query string
    Optional<User>  findByUsername(String username);

    @Query("select u from User u where u.username=?1")
    Optional<User> getUserByUsername(String username);
}
