package com.example.conferenceroomreservationsystem.organization;

import com.example.conferenceroomreservationsystem.SortType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/organizations")
class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    List<Organization> getAllOrganizations(@RequestParam(required = false) SortType sortType) {
        return organizationService.getOrganizations(sortType);
    }

//    @GetMapping("/addOrganization")
//    public String getAddOrganization() {
//        return "organizations/addNewOrganization";
//    }

    @PostMapping
    Organization postAddOrganization(@Valid @RequestBody Organization organization) {
        return organizationService.addOrganization(organization);
    }

    @PutMapping
    Organization updateOrganization(@Valid @RequestBody Organization organization) {
        return organizationService.editOrganization(organization);
    }

    @DeleteMapping("/{id}")
    Organization deleteOrganization(@PathVariable Long id) {
        return organizationService.deleteOrganizationById(id);
    }

    @GetMapping("/{name}")
    Organization getOrganizationByName(@PathVariable String name) {
        return organizationService.getOrganizationByName(name);
    }

}
