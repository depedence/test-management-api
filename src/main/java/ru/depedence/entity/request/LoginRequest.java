package ru.depedence.entity.request;

public record LoginRequest(
        String email,
        String password
) {}