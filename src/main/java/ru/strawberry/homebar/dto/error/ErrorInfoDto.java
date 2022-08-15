package ru.strawberry.homebar.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO with error from service.
 *
 * @author RBeschastnykh
 */
@Data
public class ErrorInfoDto {

  @Schema(title = "HTTP code", required = true, example = "400")
  private int code;

  @Schema(title = "Message for user", required = true, example = "Unexpected error occurred")
  private String message;
}
