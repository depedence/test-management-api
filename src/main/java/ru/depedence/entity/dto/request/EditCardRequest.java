package ru.depedence.entity.dto.request;

import ru.depedence.entity.dto.response.CardStatus;

public record EditCardRequest(
        String title,
        String content,
        CardStatus status
) {}