package ru.strawberry.boardgame.repository.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "partys")
@Getter
@Setter
@IdClass(Party.PartyId.class)
public class Party {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "table_id")
    private Long tableId;

    @Column(name = "state")
    private Boolean state;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class PartyId implements Serializable {
        private Long userId;
        private Long tableId;
    }
}
