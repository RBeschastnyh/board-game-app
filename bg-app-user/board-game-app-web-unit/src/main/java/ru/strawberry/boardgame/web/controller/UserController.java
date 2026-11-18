package ru.strawberry.boardgame.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.strawberry.boardgame.web.data.dto.CreateUserRequest;
import ru.strawberry.boardgame.web.data.dto.UpdateTeseraUsernameRequest;
import ru.strawberry.boardgame.web.service.UserService;

import java.io.Serializable;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Serializable> createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/updateTeseraUsername")
    public ResponseEntity<Serializable> updateTeseraUsername(@RequestBody UpdateTeseraUsernameRequest request) {
        userService.updateTeseraUsername(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
