package com.codigototal.backend.usersapp.services;

import com.codigototal.backend.usersapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
//Esta clase se encarga de cargar los detalles de un usuario
// específico para la autenticación
// y la autorización dentro de una aplicación web.
@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.codigototal.backend.usersapp.models.entities.User> usuarioOptional = repository.findByUsername(username);

        //Si no existe el usuario - dar error
        if (!usuarioOptional.isPresent()) {
            throw new UsernameNotFoundException(String.format("Username  %s no existe en el sistema", username));
        }


        com.codigototal.backend.usersapp.models.entities.User user = usuarioOptional.orElseThrow();

        //Si si esxiste
        List<GrantedAuthority> authorities =
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new User(user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true, authorities);
    }
}
