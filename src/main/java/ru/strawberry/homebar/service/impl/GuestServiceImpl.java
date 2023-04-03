package ru.strawberry.homebar.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.strawberry.homebar.dto.GuestDto;
import ru.strawberry.homebar.exception.GuestAlreadyExistsException;
import ru.strawberry.homebar.domain.repository.GuestRepository;
import ru.strawberry.homebar.mapper.GuestMapper;
import ru.strawberry.homebar.service.api.GuestService;

/**
 * Implementation of {@link GuestService}.
 *
 * @author RBeschastnykh
 */
@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

  private final GuestMapper guestMapper;
  private final GuestRepository repository;

  @Override
  public void createGuest(GuestDto guestDto) throws GuestAlreadyExistsException {
    if (repository.findOneByLogin(guestDto.getLogin()).isPresent()) {
      throw new GuestAlreadyExistsException("Пользователь с таким логином уже тусит");
    }
    Optional.of(guestMapper.toEntity(guestDto))
        .map(repository::save);
  }
}
