package ru.strawberry.homebar.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.service.api.CocktailService;

/**
 * Controller for displaying info about cocktails.
 *
 * @author RBeschastnykh
 */
@RestController
@RequiredArgsConstructor
public class CocktailController {

  private final CocktailService cocktailService;

  @GetMapping("/get-all")
  public ResponseEntity<List<CocktailDto>> getAll() {
    return ResponseEntity.ok(cocktailService.getAllCocktails());
  }
}
