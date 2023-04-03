package ru.strawberry.homebar.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strawberry.homebar.domain.spec.CocktailSpecificationService;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.dto.CocktailFilterDto;
import ru.strawberry.homebar.exception.NotFoundException;
import ru.strawberry.homebar.exception.ProblemEntity;
import ru.strawberry.homebar.domain.repository.CocktailRepository;
import ru.strawberry.homebar.mapper.CocktailMapper;
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
  private final CocktailSpecificationService cocktailSpecificationService;

  @Override
  public List<CocktailDto> getAllCocktails(CocktailFilterDto filterDto) {
    return Optional.of(cocktailRepository.findAll(cocktailSpecificationService.getCocktailFilterSpec(filterDto)))
        .map(cocktailMapper::toListDto)
        .orElse(null);
  }

  @Override
  public CocktailDto getById(Long id) {
    return cocktailRepository.findById(id)
        .map(cocktailMapper::toDto)
        .orElseThrow(() -> new NotFoundException(ProblemEntity.COCKTAIL));
  }

}
