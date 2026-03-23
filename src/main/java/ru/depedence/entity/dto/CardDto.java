package ru.depedence.entity.dto;

import ru.depedence.entity.dto.response.CardStatus;

public record CardDto(
        int id,
        String title,
        String content,
        CardStatus status
) {}