package com.codigototal.backend.usersapp.repositories;

import com.codigototal.backend.usersapp.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
