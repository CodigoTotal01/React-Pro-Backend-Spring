package com.codigototal.backend.usersapp.models.dto.mapper;

import com.codigototal.backend.usersapp.models.dto.UserDto;
import com.codigototal.backend.usersapp.models.entities.User;

//SOLID - Patron builder
public class DtoMapperUser {

    private static  DtoMapperUser mapper;

    private User user;

    private DtoMapperUser() {
    }
    public static DtoMapperUser getInstance(){
        mapper = new DtoMapperUser();
        return mapper;
    }

    public DtoMapperUser setUser(User user){
        this.user = user;
        return mapper;
    }

    public UserDto build(){
        if(user == null){
            throw new RuntimeException("Debe Pasar el entity User!");
        }

        return new UserDto(this.user.getId(), user.getUsername(), user.getEmail());
    }

}
