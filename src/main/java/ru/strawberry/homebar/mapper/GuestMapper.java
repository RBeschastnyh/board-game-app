package ru.strawberry.homebar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.strawberry.homebar.dto.GuestDto;
import ru.strawberry.homebar.domain.entity.Guest;

/**
 * Guest entities mapper.
 *
 * @author RBeschastnykh
 */
@Mapper(componentModel = "spring")
public interface GuestMapper {

  @Mapping(target = "id", ignore = true)
  Guest toEntity(GuestDto guestDto);
}
