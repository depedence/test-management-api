package ru.depedence.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.depedence.entity.Company;
import ru.depedence.entity.request.RegisterRequest;
import ru.depedence.repository.CompanyRepository;

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

}