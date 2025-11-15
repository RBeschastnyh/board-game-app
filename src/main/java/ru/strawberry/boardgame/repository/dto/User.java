package ru.strawberry.boardgame.repository.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tg_id")
    private Long tgId;

    @Column(name = "tesera_name")
    private String teseraName;

    @Column(name = "tesera_id")
    private Long teseraId;
}
