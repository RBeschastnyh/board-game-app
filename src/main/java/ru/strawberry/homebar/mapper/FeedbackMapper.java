package ru.strawberry.homebar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.strawberry.homebar.dto.FeedbackDto;
import ru.strawberry.homebar.entity.Feedback;

/**
 * Mapper for feedback entities.
 *
 * @author RBeschastnykh
 */
@Mapper(componentModel = "spring")
public interface FeedbackMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "feedbackText", source = "text")
  @Mapping(target = "rating", source = "rate")
  Feedback toEntity(FeedbackDto feedbackDto);

  @Mapping(target = "text", source = "feedbackText")
  @Mapping(target = "rate", source = "rating")
  @Mapping(target = "authorId", source = "guest.id")
  FeedbackDto toDto(Feedback source);
}
