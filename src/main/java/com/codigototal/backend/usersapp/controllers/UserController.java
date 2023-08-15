package com.codigototal.backend.usersapp.controllers;

import com.codigototal.backend.usersapp.models.entities.User;
import com.codigototal.backend.usersapp.services.UserService;
import jakarta.validation.Valid;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> list() {
        return userService.findAll();
    }

    //Path Variable
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    // Valida el objeto user @Valid, y para revibir errorers al lado del objeto se le pone lel objeto bindig result
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user,BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<User> userOld = userService.update(user, id);
        if(userOld.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(userOld.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id){

        Optional<User> o = userService.findById(id);

        if(o.isPresent()){
            userService.remove(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }

    //Metodos utilitarios
    private ResponseEntity<?> validation(BindingResult result) {

        Map<String, String> errors  = new HashMap<>();

        result.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), "El campo " +fieldError.getField()+ " " + fieldError.getDefaultMessage());
        });

        return  ResponseEntity.badRequest().body(errors);
    }




}
