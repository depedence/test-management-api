package ru.depedence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.depedence.entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {}