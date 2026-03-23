package ru.depedence.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.depedence.entity.dto.CardDto;
import ru.depedence.entity.dto.request.CreateCardRequest;
import ru.depedence.entity.dto.request.EditCardRequest;
import ru.depedence.entity.dto.response.MessageResponse;
import ru.depedence.service.CardService;

import java.util.List;

@RestController
@RequestMapping("/api/card")
@RequiredArgsConstructor
public class CardRestController {

    private final CardService cardService;

    @PostMapping
    public CardDto createCard(@RequestBody CreateCardRequest request) {
        return cardService.create(request);
    }

    @GetMapping("/{id}")
    public CardDto getCard(@PathVariable int id) {
        return cardService.findById(id);
    }

    @GetMapping
    public List<CardDto> getAllCards() {
        return cardService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> editCard(@PathVariable int id, @RequestBody EditCardRequest request) {
        cardService.edit(id, request);
        return ResponseEntity.ok(new MessageResponse("Card successfully edited"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCard(@PathVariable int id) {
        cardService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Card successfully deleted"));
    }

}