package com.codigototal.backend.usersapp.services;

import com.codigototal.backend.usersapp.models.dto.UserDto;
import com.codigototal.backend.usersapp.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAll();

    Optional<UserDto> findById(Long id);

    UserDto save(User user);

    Optional<UserDto> update (User user, Long id);

    void remove(Long id);

}
