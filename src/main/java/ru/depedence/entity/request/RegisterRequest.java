package ru.depedence.entity.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull
        @NotBlank
        String companyName,

        @NotNull
        @NotBlank
        String email,

        @NotNull
        @NotBlank
        String password
) {}