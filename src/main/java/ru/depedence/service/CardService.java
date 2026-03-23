package ru.depedence.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.depedence.entity.Card;
import ru.depedence.entity.dto.CardDto;
import ru.depedence.entity.dto.request.CreateCardRequest;
import ru.depedence.entity.dto.request.UpdateCardRequest;
import ru.depedence.repository.CardRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CardService {

    private final CardRepository repository;

    public CardDto create(CreateCardRequest request) {
        Card card = new Card();

        card.setTitle(request.title());
        card.setContent(request.content());
        card.setStatus(request.status());

        repository.save(card);
        return card.toDto();
    }

    public CardDto findById(int id) {
        return repository.findById(id)
                .map(Card::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
    }

    public void edit(int id, UpdateCardRequest request) {
        Card card = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));

        card.setTitle(request.title());
        card.setContent(request.content());
        card.setStatus(request.status());

        repository.save(card);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

}