package ru.depedence.entity.dto.request;

public record LoginRequest(
        String email,
        String password
) {}