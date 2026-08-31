package ru.strawberry.boardgame.service.tesera.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeseraGame {
    private Long id;
    private Long teseraId;
    private String title;
    private String alias;
    private Double ratingMy;
    private String teseraUrl;
    private String photoUrl;
    private Boolean isAddition;
}
