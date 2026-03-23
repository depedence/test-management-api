package ru.depedence.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.depedence.entity.Company;
import ru.depedence.entity.dto.CompanyDto;
import ru.depedence.entity.dto.request.EditCompanyRequest;
import ru.depedence.repository.CompanyRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository repository;

    public CompanyDto findById(int id) {
        return repository.findById(id)
                .map(Company::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));
    }

    public void edit(int id, EditCompanyRequest request) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found"));

        if (!company.getCompanyName().equals(request.companyName()) && repository.existsByCompanyName(request.companyName())) {
            throw new IllegalArgumentException("Company name already exists");
        }
        if (!company.getEmail().equals(request.email()) && repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        company.setCompanyName(request.companyName());
        company.setEmail(request.email());

        repository.save(company);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

}