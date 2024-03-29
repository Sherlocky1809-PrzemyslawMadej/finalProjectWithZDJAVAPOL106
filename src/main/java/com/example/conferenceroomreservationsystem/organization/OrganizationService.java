package com.example.conferenceroomreservationsystem.organization;

import com.example.conferenceroomreservationsystem.SortType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
class OrganizationService {

    private final OrganizationRepository organizationRepository;

    OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    Organization addOrganization(Organization organization) {
        organizationRepository.findByName(organization.getName())
                .ifPresent(o -> {
                    throw new IllegalArgumentException("Organization already exists!");
                });

        return organizationRepository.save(organization);
    }

    List<Organization> getOrganizations(SortType sortType) {
        if(sortType != null) {
            return organizationRepository.findAll(sortType.getSort("name"));
        } else {
            return organizationRepository.findAll();
        }
    }

//    Organization getOrganizationById(Long id){
//        return organizationRepository.findById(id).orElse(null);
//    }

    Organization editOrganization(Organization organization) {
        Organization editedOrganization = organizationRepository.findById(organization.getId())
                .orElseThrow(() -> new NoSuchElementException("No organization to update found!"));
        organizationRepository.findByName(organization.getName())
                .ifPresent(o -> {

                    throw new IllegalArgumentException("Organization already exists!");
                });
        if(organization.getName() != null) {
            editedOrganization.setName(organization.getName());
        }
        return organizationRepository.save(editedOrganization);
    }

    Organization deleteOrganizationById(Long id) {
        Organization organizationToDelete = organizationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No organization found!"));
        organizationRepository.delete(organizationToDelete);
        return organizationToDelete;
    }

    Organization getOrganizationByName(String name) {
        return organizationRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("No organization found by name!"));
    }

}
