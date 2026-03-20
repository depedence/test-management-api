package ru.depedence.entity.request;

public record EditCompanyRequest(
        String companyName,
        String email
) {}