package com.example.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class OrganizationServiceTest {

    @TestConfiguration
    static class OrganizationServiceTestConfiguration {
        @Bean
        public OrganizationService organizationService(OrganizationRepository organizationRepository) {
            return new OrganizationService(organizationRepository);
        }
    }

    @MockBean
    OrganizationRepository organizationRepository;

    @Autowired
    OrganizationService organizationService;

    @Test
    void should_add_organization_if_repository_not_contain_organization_by_given_name() {
        // given
        String name = "test1";
        Organization organizationToAdd = new Organization(name);
        Mockito.when(organizationRepository.findByName(name)).thenReturn(Optional.empty());
        // when
        organizationService.addOrganization(organizationToAdd);
        // then
        Mockito.verify(organizationRepository).save(organizationToAdd);
    }

    @Test
    void should_throw_illegal_argument_exception_if_add_organization_by_existing_name() {
        // given
        String name = "test1";
        Organization organizationToAdd = new Organization(name);
        Mockito.when(organizationRepository.findByName(organizationToAdd.getName()))
                .thenReturn(Optional.of(organizationToAdd));
       // when & then
        assertThatThrownBy(() -> organizationService.addOrganization(organizationToAdd)).
                isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Organization already exists!");
    }

    @Test
    void should_delete_organization_from_repo_by_existing_name() {
        // given
        Organization organizationToDelete = new Organization(1L, "");
        Mockito.when(organizationRepository.findById(1L)).thenReturn(Optional.of(organizationToDelete));
        // when
        organizationService.deleteOrganizationById(1L);
        // then
        Mockito.verify(organizationRepository).delete(organizationToDelete);
    }

    @Test
    void should_throw_no_such_element_exception_if_deleted_organization_by_name_not_exist() {
        // given
        Mockito.when(organizationRepository.findById(1L)).thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> organizationService.deleteOrganizationById(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No organization found!");
    }

    @Test
    void when_get_all_organizations_in_asc_order_then_sort_by_name_in_asc_direction_param_should_be_provided_to_repo() {
        // given
        SortType sortType = SortType.ASC;
        ArgumentCaptor<Sort> sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
        // when
        organizationService.getOrganizations(sortType);
        // then
        Mockito.verify(organizationRepository).findAll(sortArgumentCaptor.capture());
        assertEquals(Sort.by(Sort.Direction.ASC, "name"), sortArgumentCaptor.getValue());
    }

    @Test
    void when_get_all_organizations_in_desc_order_then_sort_by_name_in_asc_direction_param_should_be_provided_to_repo() {
        // given
        SortType sortType = SortType.DESC;
        ArgumentCaptor<Sort> sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);
        // when
        organizationService.getOrganizations(sortType);
        // then
        Mockito.verify(organizationRepository).findAll(sortArgumentCaptor.capture());
        assertEquals(Sort.by(Sort.Direction.DESC, "name"), sortArgumentCaptor.getValue());
    }

    @Test
    void when_get_all_organization_without_sort_type_then_no_param_should_be_provided_to_repo() {
        // given
        SortType sortType = null;
        // when
        organizationService.getOrganizations(sortType);
        // then
        Mockito.verify(organizationRepository).findAll();
    }

    @Test
    void should_update_organization_with_new_parameters() {
        // given
        ArgumentCaptor<Organization> organizationArgumentCaptor = ArgumentCaptor.forClass(Organization.class);
        Organization existingOrganization = new Organization(1L, "elo");
        Organization organization = new Organization(1L, "white");
        Mockito.when(organizationRepository.findById(organization.getId()))
                .thenReturn(Optional.of(existingOrganization));
        Mockito.when(organizationRepository.findByName(organization.getName()))
                .thenReturn(Optional.empty());
        // when
        organizationService.editOrganization(organization);
        // then
        Mockito.verify(organizationRepository).save(organizationArgumentCaptor.capture());
        Organization updatedOrganization = organizationArgumentCaptor.getValue();
        assertEquals(1L, updatedOrganization.getId());
        assertEquals("white", updatedOrganization.getName());
    }

    @Test
    void should_throw_no_such_element_exception_if_organization_to_update_not_found() {
        // given
        Organization organization = new Organization(1L, "test");
        Mockito.when(organizationRepository.findById(1L)).thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> organizationService.editOrganization(organization))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No organization to update found!");
    }

    @Test
    void should_return_argument_exception_if_given_name_organization_exists() {
        // given
        Organization existingOrganization = new Organization(1L, "xxxx");
        Organization organization = new Organization(1L, "test");
        Mockito.when(organizationRepository.findById(organization.getId())).thenReturn(Optional.of(existingOrganization));
        Mockito.when(organizationRepository.findByName(organization.getName())).thenReturn(Optional.of(organization));
        // when & then
        assertThatThrownBy(() -> organizationService.editOrganization(organization))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Organization already exists!");
    }

    @Test
    void should_find_organization_by_given_name() {
        // given
        Organization organization = new Organization(1L, "test");
        Mockito.when(organizationRepository.findByName(organization.getName())).thenReturn(Optional.of(organization));
        // when
        organizationService.getOrganizationByName(organization.getName());
        // then
        Mockito.verify(organizationRepository).findByName(organization.getName());
    }

    @Test
    void should_throw_no_such_element_exception_if_no_found_organization_by_given_name() {
        // given
        Organization organization = new Organization(1L, "test");
        Mockito.when(organizationRepository.findByName(organization.getName())).thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> organizationService.getOrganizationByName(organization.getName()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No organization found by name!");
    }

}