package ru.strawberry.homebar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.dto.CocktailFilterDto;
import ru.strawberry.homebar.dto.error.ErrorInfoDto;
import ru.strawberry.homebar.service.api.CocktailService;

/**
 * API controller for cocktails operations.
 *
 * @author RBeschastnykh
 */
@RestController
@RequestMapping("/cocktails")
@RequiredArgsConstructor
@Tag(name = "cocktail api", description = "Работа с коктейлями")
public class CocktailController {

  private final CocktailService cocktailService;

  @PostMapping()
  @Operation(tags = {"cocktail api"}, summary = "Получение списка всех коктелей")
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
  public ResponseEntity<List<CocktailDto>> getCocktails(@RequestBody CocktailFilterDto filterDto) {
    return ResponseEntity.ok(cocktailService.getAllCocktails(filterDto));
  }

  @GetMapping("/{id}")
  @Operation(tags = {"cocktail api"}, summary = "Получение коктейля по идентификатору")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "Успешно получен коктейль",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = CocktailDto.class))
          ),
          @ApiResponse(
              responseCode = "500",
              description = "Unknown error",
              content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfoDto.class))
          )
      })
  public ResponseEntity<CocktailDto> getCocktailById(@PathVariable Long id) {
    return ResponseEntity.ok(cocktailService.getById(id));
  }
}
