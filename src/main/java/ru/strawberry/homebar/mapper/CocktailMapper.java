package ru.strawberry.homebar.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.domain.entity.Cocktail;

/**
 * Mapper for cocktail entities.
 *
 * @author RBeschastnykh
 */
@Mapper(componentModel = "spring")
public interface CocktailMapper {

  List<CocktailDto> toListDto(List<Cocktail> source);

  CocktailDto toDto(Cocktail source);
}
