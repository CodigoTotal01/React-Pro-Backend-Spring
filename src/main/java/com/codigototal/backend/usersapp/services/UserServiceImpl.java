package com.codigototal.backend.usersapp.services;

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

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    @Override
    @Transactional
    public User save(User user) {

        String passwordBC = passwordEncoder.encode(user.getPassword());

        user.setPassword(passwordBC);

        Optional<Role> roleOptionalBD = roleRepository.findByName("ROLE_USER");
        List<Role> roles= new ArrayList<>();
        if(roleOptionalBD.isPresent()){
            roles.add(roleOptionalBD.orElseThrow());
        };

        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> update(User user, Long id) {
        Optional<User> o = this.findById(id);
        User userOptional = null;
        if(o.isPresent()){
            User userDB = o.orElseThrow();
            userDB.setUsername(user.getUsername());
            userDB.setEmail(user.getEmail());
            userOptional = this.save(userDB);
        }
        return Optional.ofNullable(userOptional);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        userRepository.deleteById(id);
    }
}
