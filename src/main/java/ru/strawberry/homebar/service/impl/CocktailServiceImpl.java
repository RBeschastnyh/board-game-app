package ru.strawberry.homebar.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.mapper.CocktailMapper;
import ru.strawberry.homebar.repository.CocktailRepository;
import ru.strawberry.homebar.service.api.CocktailService;

/**
 * Implementation of {@link CocktailService}.
 *
 * @author RBeschastnykh
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CocktailServiceImpl implements CocktailService {

  private final CocktailRepository cocktailRepository;
  private final CocktailMapper cocktailMapper;

  @Override
  public List<CocktailDto> getAllCocktails() {
    return Optional.of(cocktailRepository.findAll())
        .map(cocktailMapper::toListDto)
        .orElse(null);
  }

  @Override
  public List<CocktailDto> getAllCocktailsApplyFilter() {
    return null;
  }
}
