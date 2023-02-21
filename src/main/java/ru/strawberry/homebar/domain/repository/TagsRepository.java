package ru.strawberry.homebar.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.strawberry.homebar.domain.entity.Tags;

public interface TagsRepository extends JpaRepository<Tags, Long> {

}
