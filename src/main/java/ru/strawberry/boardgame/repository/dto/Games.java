package ru.strawberry.boardgame.repository.dto;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tesera_id")
    private Long teseraId;

    @Column(name = "title")
    private String title;

    @Column(name = "alias")
    private String alias;

    @Column(name = "rating_my")
    private Double ratingMy;

    @Column(name = "tesera_url")
    private String teseraUrl;

    @Column(name = "players_min")
    private Integer playersMin;

    @Column(name = "players_max")
    private Integer playersMax;

    @Column(name = "players_min_recommend")
    private Integer playersMinRecommended;

    @Column(name = "players_max_recommend")
    private Integer playersMaxRecommended;

    @Column(name = "playtime_min")
    private Integer playtimeMin;

    @Column(name = "playtime_max")
    private Integer playtimeMax;

    @Column(name = "is_addition")
    private Boolean isAddition;
}
