package com.codigototal.backend.usersapp.repositories;

import com.codigototal.backend.usersapp.models.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    //Emeplando nombre del metodo en vez de query string
    Optional<Role>  findByName(String name);

}
