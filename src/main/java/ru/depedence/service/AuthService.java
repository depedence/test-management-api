package ru.depedence.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.depedence.entity.Company;
import ru.depedence.entity.dto.CompanyDto;
import ru.depedence.entity.dto.request.RegisterRequest;
import ru.depedence.repository.CompanyRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public Company register(RegisterRequest request) {
        Company company = new Company();

        company.setCompanyName(request.companyName());
        company.setEmail(request.email());
        company.setPassword(passwordEncoder.encode(request.password()));

        companyRepository.save(company);
        return company;
    }

    public CompanyDto me() {
        Company company = (Company) Objects.requireNonNull(SecurityContextHolder
                .getContext()
                .getAuthentication())
                .getPrincipal();

        return Objects.requireNonNull(company).toDto();
    }

}