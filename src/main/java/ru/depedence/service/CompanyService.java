package ru.depedence.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.depedence.repository.CompanyRepository;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;



}