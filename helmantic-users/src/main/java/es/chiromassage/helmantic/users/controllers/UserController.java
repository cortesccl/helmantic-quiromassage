package es.chiromassage.helmantic.users.controllers;

import es.chiromassage.helmantic.users.models.entity.User;
import es.chiromassage.helmantic.users.services.UserDetailsService;
import es.chiromassage.helmantic.users.services.UserService;
import es.chiromassage.helmantic.users.services.exceptions.UserNotCreatedException;
import es.chiromassage.helmantic.users.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired//(required=true)
    private UserService userService;
    private UserDetailsService userDetailsService;

    @GetMapping
    public ResponseEntity<List<User>> listUsers () {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        User user = userService.findUserById(id).orElse(null);
        if (null == user) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {

        try {
            if (result.hasErrors()) {
                return validate(result);
            }
            User userCreate = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreate);
        } catch (UserNotCreatedException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody User user, BindingResult result) {
//        if (userService.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isPresent()) {
//            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Ya  existe un usuario con ese correo o username"));
//        }
        try {
            if (result.hasErrors()) {
                return validate(result);
            }
            user.setId(id);
            User userDB = userService.updateUser(id, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDB);
        } catch (UserNotCreatedException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long  id) {
        try {
            userService.removeUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/users-course")
    public ResponseEntity<?> findUsersByCourse (@RequestParam List<Long> ids) {
        return ResponseEntity.ok(userService.findAllUsersById(ids));
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
