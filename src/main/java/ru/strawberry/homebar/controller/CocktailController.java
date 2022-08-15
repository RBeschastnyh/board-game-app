package ru.strawberry.homebar.controller;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.dto.FeedbackDto;
import ru.strawberry.homebar.dto.error.ErrorInfoDto;
import ru.strawberry.homebar.exception.NotFoundException;
import ru.strawberry.homebar.service.api.CocktailService;

/**
 * API controller for cocktails operations.
 *
 * @author RBeschastnykh
 */
@RestController
@RequestMapping("/cocktails")
@RequiredArgsConstructor
public class CocktailController {

  private final CocktailService cocktailService;

  @GetMapping()
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CocktailDto.class)))),
        @ApiResponse(
            responseCode = "500",
            description = "Unknown error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfoDto.class)))
      })
  public ResponseEntity<List<CocktailDto>> getAll() {
    return ResponseEntity.ok(cocktailService.getAllCocktails());
  }

  @PostMapping("/{cocktailId}/rate")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CocktailDto.class)))),
        @ApiResponse(
            responseCode = "500",
            description = "Unknown error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfoDto.class)))
      })
  public ResponseEntity<?> createRate(@PathVariable Long cocktailId, @RequestBody FeedbackDto feedback) throws NotFoundException {
    cocktailService.createFeedback(cocktailId, feedback);
    return new ResponseEntity<>(null, HttpStatus.CREATED);
  }
}
