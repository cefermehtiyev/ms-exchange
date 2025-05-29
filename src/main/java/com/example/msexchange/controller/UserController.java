package com.example.msexchange.controller;

import com.example.msexchange.model.repsone.UserResponse;
import com.example.msexchange.model.request.UserRequest;
import com.example.msexchange.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void registerUser(@RequestBody UserRequest userRequest){
        userService.registerUser(userRequest);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUser(){
        return userService.getAllUsers();
    }

    @PutMapping("{id}")
    public void updateUser(@PathVariable Long id,@RequestBody UserRequest userRequest){
        userService.updateUser(id, userRequest);
    }
}
