package ru.depedence.entity.dto.request;

import ru.depedence.entity.Card;
import ru.depedence.entity.dto.response.CardStatus;

public record CreateCardRequest(
        String title,
        String content,
        CardStatus status
) {
    public Card toEntity() {
        return new Card(title, content, status);
    }
}