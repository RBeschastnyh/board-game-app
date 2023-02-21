package ru.strawberry.homebar.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.strawberry.homebar.dto.TagDto;
import ru.strawberry.homebar.service.api.TagsService;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagsController {

  private final TagsService tagService;

  @GetMapping("/")
  @Operation(
      tags = {"cocktail-tags"},
      description = "Получение списка всех тэгов коктейлей"
  )
  public ResponseEntity<List<TagDto>> getAllTags() {
    return ResponseEntity.ok(tagService.getAllTags());
  }

}
