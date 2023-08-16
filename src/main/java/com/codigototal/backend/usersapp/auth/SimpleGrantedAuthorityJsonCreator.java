package com.codigototal.backend.usersapp.auth;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreator {

    //se poblara el valor de role
    @JsonCreator
    public SimpleGrantedAuthorityJsonCreator(@JsonProperty("authority") String role){
    }

}
