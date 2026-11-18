package ru.strawberry.boardgame.web.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class UpdateTeseraUsernameRequest {
    private String username;
    private int teseraUserId;
    private String teseraUsername;
}
