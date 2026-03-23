package ru.depedence.entity.dto.request;

public record EditCompanyRequest(
        String companyName,
        String email
) {}