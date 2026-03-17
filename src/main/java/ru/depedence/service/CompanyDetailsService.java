package ru.depedence.service;

import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.depedence.entity.Company;
import ru.depedence.repository.CompanyRepository;

@Service
public class CompanyDetailsService implements UserDetailsService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyDetailsService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Company company = companyRepository.findByEmail(email);

        if (company == null) throw new UsernameNotFoundException("Company not found");

        return company;
    }

}