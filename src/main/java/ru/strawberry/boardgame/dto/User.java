package ru.strawberry.boardgame.dto;

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
    public Long id;

    @Column(name = "tg_id")
    public Long tgId;

    @Column(name = "tesera_name")
    public String teseraName;
}
