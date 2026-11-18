package ru.strawberry.boardgame.web.service;

import ru.strawberry.boardgame.web.data.dto.CreateUserRequest;
import ru.strawberry.boardgame.web.data.dto.UpdateTeseraUsernameRequest;

public interface UserService {

    void createUser(CreateUserRequest request);

    void updateTeseraUsername(UpdateTeseraUsernameRequest request);
}
