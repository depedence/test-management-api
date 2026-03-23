package ru.depedence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.depedence.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByIdAndCompanyId(int id, int companyId);
    List<Card> findAllByCompanyId(int companyId);
}