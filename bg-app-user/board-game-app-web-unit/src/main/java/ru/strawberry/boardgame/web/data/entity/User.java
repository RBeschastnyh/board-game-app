package ru.strawberry.boardgame.web.data.entity;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode(exclude = {"password"})
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private UUID id = UUID.randomUUID();
    private String email;
    private String login;
    private String username;
    private String password;
}
