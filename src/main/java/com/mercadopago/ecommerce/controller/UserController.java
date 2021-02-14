package com.mercadopago.ecommerce.controller;

import com.mercadopago.ecommerce.domain.model.User;
import com.mercadopago.ecommerce.domain.service.UserService;
import com.mercadopago.ecommerce.resource.SaveUserResource;
import com.mercadopago.ecommerce.resource.UserResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Tag(name = "users", description = "Users API")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://ecommerce-open.wep.app")
public class UserController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Page<UserResource>getAllUsers(Pageable pageable){
        Page<User> userPage = userService.getAllUsers(pageable);
        List<UserResource> resourceList = userPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resourceList, pageable, resourceList.size());
    }

    @GetMapping("/users/{id}")
    public UserResource getUserById(@Parameter(description ="userId") @PathVariable(name = "id") Long userId){
        return convertToResource(userService.getUserById(userId));
    }

    @PostMapping("/users")
    public UserResource createUser(@Valid @RequestBody SaveUserResource resource){
        User user = convertToEntity(resource);
        return convertToResource(userService.createUser(user));
    }

    @PutMapping("/users/{id}")
    public UserResource uptadeUser(@PathVariable(name = "id") Long userId, @Valid @RequestBody SaveUserResource resource){
        User user = convertToEntity(resource);
        return convertToResource(userService.updateUser(userId, user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable(name = "id") Long userId){
        return userService.deleteUser(userId);
    }

    private User convertToEntity(SaveUserResource resource){
        return mapper.map(resource, User.class);
    }

    private UserResource convertToResource(User entity){
        return mapper.map(entity, UserResource.class);
    }
}
