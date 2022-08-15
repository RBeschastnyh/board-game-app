package ru.strawberry.homebar.mapper;

import org.mapstruct.Mapper;
import ru.strawberry.homebar.dto.GuestDto;
import ru.strawberry.homebar.entity.Guest;

/**
 * Guest entities mapper.
 *
 * @author RBeschastnykh
 */
@Mapper(componentModel = "spring")
public interface GuestMapper {

  Guest toEntity(GuestDto guestDto);
}
