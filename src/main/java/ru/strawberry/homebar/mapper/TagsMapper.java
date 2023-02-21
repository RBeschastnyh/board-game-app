package ru.strawberry.homebar.mapper;

import org.mapstruct.Mapper;
import ru.strawberry.homebar.domain.entity.Tags;
import ru.strawberry.homebar.dto.TagDto;

@Mapper(componentModel = "spring")
public interface TagsMapper {
  TagDto toTagDto(Tags source);
}
