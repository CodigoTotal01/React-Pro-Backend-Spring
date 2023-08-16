package com.codigototal.backend.usersapp.models.dto.mapper;

import com.codigototal.backend.usersapp.models.dto.UserDto;
import com.codigototal.backend.usersapp.models.entities.User;

//SOLID - Patron builder - Singleton - retornara uan histancia propia para cada puerco
public class DtoMapperUser {

    private User user;

    private DtoMapperUser() {
    }
    public static DtoMapperUser builder(){
        return new DtoMapperUser();
    }

    public DtoMapperUser setUser(User user){
        this.user = user;
        return this;
    }

    public UserDto build(){
        if(user == null){
            throw new RuntimeException("Debe Pasar el entity User!");
        }
        return new UserDto(this.user.getId(), user.getUsername(), user.getEmail());
    }

}
