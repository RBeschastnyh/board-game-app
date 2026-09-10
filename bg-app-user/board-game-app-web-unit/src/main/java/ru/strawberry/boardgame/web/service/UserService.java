package ru.strawberry.boardgame.web.service;

import ru.strawberry.boardgame.web.data.dto.CreateUserRequest;

public interface UserService {

    void createUser(CreateUserRequest request);
}
