package com.codigototal.backend.usersapp.services;

import com.codigototal.backend.usersapp.models.dto.UserDto;
import com.codigototal.backend.usersapp.models.dto.mapper.DtoMapperUser;
import com.codigototal.backend.usersapp.models.entities.Role;
import com.codigototal.backend.usersapp.models.entities.User;
import com.codigototal.backend.usersapp.repositories.RoleRepository;
import com.codigototal.backend.usersapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
                .map(user -> DtoMapperUser.builder()
                        .setUser(user)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findById(Long id) {
        return userRepository
                .findById(id)
                .map(user -> DtoMapperUser.builder()
                .setUser(user)
                .build());
    }


    @Override
    @Transactional
    public UserDto save(User user) {

        String passwordBC = passwordEncoder.encode(user.getPassword());

        user.setPassword(passwordBC);

        Optional<Role> roleOptionalBD = roleRepository.findByName("ROLE_USER");

        List<Role> roles = new ArrayList<>();1
        if (roleOptionalBD.isPresent()) {
            roles.add(roleOptionalBD.orElseThrow());
        }

        user.setRoles(roles);

        return DtoMapperUser.builder().setUser(userRepository.save(user)).build();
    }

    @Override
    public Optional<UserDto> update(User user, Long id) {
        Optional<User> o = userRepository.findById(id);
        User userOptional = null;
        if (o.isPresent()) {
            User userDB = o.orElseThrow();
            userDB.setUsername(user.getUsername());
            userDB.setEmail(user.getEmail());
            userOptional = userRepository.save(userDB);
        }
        return Optional.ofNullable(DtoMapperUser.builder().setUser(userOptional).build());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}
