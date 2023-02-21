package ru.strawberry.homebar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.strawberry.homebar.dto.GuestDto;
import ru.strawberry.homebar.exception.GuestAlreadyExistsException;
import ru.strawberry.homebar.service.api.GuestService;

/**
 * API controller for guest operations.
 *
 * @author RBeschastnykh
 */
@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
@Tag(name = "guest api", description = "Работа с гостями")
public class GuestController {

  private final GuestService guestService;

  @PostMapping("/create")
  @Operation(tags = {"guest api"}, summary = "Создание гостевого пользователя")
  public ResponseEntity<?> createGuest(@RequestBody GuestDto guestDto) throws GuestAlreadyExistsException {
    guestService.createGuest(guestDto);
    return new ResponseEntity<>(null, HttpStatus.CREATED);
  }
}
