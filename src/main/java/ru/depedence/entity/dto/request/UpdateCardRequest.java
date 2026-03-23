package ru.depedence.entity.dto.request;

import ru.depedence.entity.dto.response.CardStatus;

public record UpdateCardRequest(
        String title,
        String content,
        CardStatus status
) {}