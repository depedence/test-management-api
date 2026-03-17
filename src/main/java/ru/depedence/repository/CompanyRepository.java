package ru.depedence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.depedence.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Company findByEmail(String email);
}