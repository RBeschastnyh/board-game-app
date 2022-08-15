package ru.strawberry.homebar.service.api;

import ru.strawberry.homebar.dto.GuestDto;
import ru.strawberry.homebar.exception.GuestAlreadyExistsException;

/**
 * Service for managing guest operations.
 *
 * @author RBeschastnykh
 */
public interface GuestService {

  /**
   * Method creates user in system.
   *
   * @param guestDto - guest info
   * @see GuestDto
   */
  void createGuest(GuestDto guestDto) throws GuestAlreadyExistsException;
}
