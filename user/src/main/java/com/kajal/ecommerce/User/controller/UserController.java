package com.kajal.ecommerce.User.controller;

import com.kajal.ecommerce.User.DTO.UserRequest;
import com.kajal.ecommerce.User.DTO.UserResponse;
import com.kajal.ecommerce.User.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/users")
public class UserController
{


    private final UserService userService;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("/users/get")
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        return new ResponseEntity<>(userService.fetchAllUsers(),HttpStatus.OK);
    }

    @PostMapping("/users/add")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest)
    {
        userService.addUser(userRequest);
        return new ResponseEntity<>("User added successfully",HttpStatus.OK);
    }

    @GetMapping("/users/get/{id}")
    public ResponseEntity<UserResponse> fetchById(@PathVariable String id)
    {
        Optional<UserResponse> user= userService.fetchUserById(id);

        if(user.isPresent())
        {
            return new ResponseEntity<>(user.get(),HttpStatus.OK );
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        return user.map
//                (userResponse -> new ResponseEntity<>
//                        (userResponse, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));


    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<String> fetchById(@PathVariable String id,@RequestBody UserRequest updatedUserRequest)
    {
        boolean updated= userService.updateUser(id,updatedUserRequest);

        if(updated==true)
        {
            return new ResponseEntity<>("Updated successfully",HttpStatus.OK );
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
