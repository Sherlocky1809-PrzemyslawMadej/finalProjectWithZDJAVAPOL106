package com.example.conferenceroomreservationsystem.organization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organizations")
class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    List<Organization> getAllOrganizations() {
        return organizationService.getOrganizations();
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

    @DeleteMapping
    Organization deleteOrganization(@PathVariable("/{id}") Long id) {
        return organizationService.deleteOrganizationById(id);
    }

//    Obsługa wyjątku - dzięki temu będziemy mieli komunikat dla użytkownika dlaczego wprowadzone nie są
//    ważne:
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
