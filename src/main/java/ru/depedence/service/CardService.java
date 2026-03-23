package ru.depedence.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.depedence.entity.Card;
import ru.depedence.entity.Company;
import ru.depedence.entity.dto.CardDto;
import ru.depedence.entity.dto.request.CreateCardRequest;
import ru.depedence.entity.dto.request.UpdateCardRequest;
import ru.depedence.repository.CardRepository;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CardService {

    private final CardRepository repository;

    private Company getCurrentCompany() {
        return (Company) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication())
                .getPrincipal();
    }

    public CardDto create(CreateCardRequest request) {
        Company company = getCurrentCompany();
        Card card = new Card();

        card.setTitle(request.title());
        card.setContent(request.content());
        card.setStatus(request.status());
        card.setCompany(company);

        repository.save(card);
        return card.toDto();
    }

    public CardDto findById(int id) {
        int companyId = getCurrentCompany().getId();
        return repository.findByIdAndCompanyId(id, companyId)
                .map(Card::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
    }

    public List<CardDto> findAll() {
        int companyId = getCurrentCompany().getId();
        return repository.findAllByCompanyId(companyId)
                .stream().map(Card::toDto).toList();
    }

    public void edit(int id, UpdateCardRequest request) {
        int companyId = getCurrentCompany().getId();
        Card card = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));

        card.setTitle(request.title());
        card.setContent(request.content());
        card.setStatus(request.status());

        repository.save(card);
    }

    public void delete(int id) {
        int companyId = getCurrentCompany().getId();
        Card card = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found"));
        repository.delete(card);
    }

}