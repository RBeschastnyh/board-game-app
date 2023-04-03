package ru.strawberry.homebar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Guest DTO.
 *
 * @author RBeschastnykh
 */
@Getter
@Setter
public class GuestDto {

  @Schema(title = "Users' login")
  private String login;

  @Schema(title = "Users' full name")
  private String fullName;

  @Schema(title = "Users' password")
  private String password;
}
