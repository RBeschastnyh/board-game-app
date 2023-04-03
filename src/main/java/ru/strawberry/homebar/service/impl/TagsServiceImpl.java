package ru.strawberry.homebar.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.strawberry.homebar.domain.repository.TagsRepository;
import ru.strawberry.homebar.mapper.TagsMapper;
import ru.strawberry.homebar.dto.TagDto;
import ru.strawberry.homebar.service.api.TagsService;

@Service
@RequiredArgsConstructor
public class TagsServiceImpl implements TagsService {

  private final TagsRepository tagRepository;
  private final TagsMapper tagsMapper;

  @Override
  public List<TagDto> getAllTags() {
    return tagRepository.findAll()
        .stream()
        .map(tagsMapper::toTagDto)
        .collect(Collectors.toList());
  }
}
