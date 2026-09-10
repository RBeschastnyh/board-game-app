package ru.strawberry.boardgame.web.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateUserRequest {

    private String username;
    private String login;
    private String password;
    private String email;
}
