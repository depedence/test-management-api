package ru.depedence.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.depedence.entity.dto.CompanyDto;
import ru.depedence.entity.dto.request.EditCompanyRequest;
import ru.depedence.entity.dto.response.MessageResponse;
import ru.depedence.service.CompanyService;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyService companyService;

    @GetMapping("/{id}")
    public CompanyDto getCompany(@PathVariable int id) {
        return companyService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> editCompany(@PathVariable int id, @RequestBody EditCompanyRequest request) {
        companyService.edit(id, request);
        return ResponseEntity.ok(new MessageResponse("Company was successfully edited"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCompany(@PathVariable int id) {
        companyService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Company was successfully deleted"));
    }

}