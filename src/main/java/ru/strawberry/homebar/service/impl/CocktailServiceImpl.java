package ru.strawberry.homebar.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.dto.FeedbackDto;
import ru.strawberry.homebar.entity.Cocktail;
import ru.strawberry.homebar.entity.Feedback;
import ru.strawberry.homebar.entity.Guest;
import ru.strawberry.homebar.exception.NotFoundException;
import ru.strawberry.homebar.mapper.CocktailMapper;
import ru.strawberry.homebar.mapper.FeedbackMapper;
import ru.strawberry.homebar.repository.CocktailRepository;
import ru.strawberry.homebar.repository.FeedbackRepository;
import ru.strawberry.homebar.repository.GuestRepository;
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

  private final GuestRepository guestRepository;
  private final FeedbackRepository feedbackRepository;
  private final FeedbackMapper feedbackMapper;

  @Override
  public List<CocktailDto> getAllCocktails() {
    return Optional.of(cocktailRepository.findAll())
        .map(cocktailMapper::toListDto)
        .orElse(null);
  }

  @Override
  public void createFeedback(Long cocktailId, FeedbackDto feedbackDto) throws NotFoundException {
    feedbackRepository
        .findByCocktailId(cocktailId)
        .map(
            feedback -> {
              feedback.setRating(feedback.getRating());
              return feedbackRepository.save(feedback);
            })
        .orElse(createRate(cocktailId, feedbackDto));
  }

  private Feedback createRate(Long cocktailId, FeedbackDto feedbackDto) throws NotFoundException {
    Guest guest = guestRepository.findById(feedbackDto.getAuthorId()).orElseThrow(() -> new NotFoundException(("Пользователь не найден!")));
    Cocktail cocktail = cocktailRepository.findById(cocktailId).orElseThrow(() -> new NotFoundException(("Коктейль не найден!")));
    Feedback feedback = feedbackMapper.toEntity(feedbackDto);
    feedback.setCocktail(cocktail);
    feedback.setGuest(guest);
    return feedbackRepository.save(feedback);
  }
}
