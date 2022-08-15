package ru.strawberry.homebar.controller;

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
public class GuestController {

  private final GuestService guestService;

  @PostMapping("/create")
  public ResponseEntity<?> createGuest(@RequestBody GuestDto guestDto) throws GuestAlreadyExistsException {
    guestService.createGuest(guestDto);
    return new ResponseEntity<>(null, HttpStatus.CREATED);
  }
}
