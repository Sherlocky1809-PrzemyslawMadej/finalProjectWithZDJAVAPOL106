package com.example.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrganizationController.class)
class OrganizationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrganizationService organizationService;

    @Test
    void should_object_be_returned_if_get_existing_organization() throws Exception {
        // given
        Mockito.when(organizationService.getOrganizationByName("test"))
                .thenReturn(new Organization(1L, "test"));
        // when
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("test")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void should_return_500_when_get_not_existing_organization() throws Exception {
        // given
        Mockito.when(organizationService.getOrganizationByName("test"))
                .thenThrow(new NoSuchElementException("Can't find proper organization!"));
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void should_return_all_organizations_if_get_exisiting_list_of_organizations() throws Exception {
        // given
        Organization organization1 = new Organization(1L, "test");
        Organization organization2 = new Organization(2L, "test2");
        List<Organization> organizations = Arrays.asList(organization1, organization2);
        Mockito.when(organizationService.getOrganizations(Mockito.any()))
                .thenReturn(organizations);
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", equalTo("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", equalTo(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", equalTo("test2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void should_return_500_if_get_empty_list_of_organizations() throws Exception {
        // given
        Mockito.when(organizationService.getOrganizations(Mockito.any()))
                .thenThrow(new NoSuchElementException("Can't find any element in organization list!"));
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void should_add_organization_to_repository() throws Exception {
        // given
        Organization organization2 = new Organization("test");
        Mockito.when(organizationService.addOrganization(organization2))
                .thenReturn(new Organization(1L, "test"));
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"test\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("test")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    }

    @Test
    void should_throw_bad_request_when_send_post_duplicated_organizations() throws Exception {
        // given
        Organization organization = new Organization("Google");
        Mockito.when(organizationService.addOrganization(organization))
                .thenThrow(new IllegalArgumentException("Item already exists"));
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"Google\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$", equalTo("Item already exists")));
    }

    @Test
    void should_return_deleted_organization() throws Exception {
        // given
        Organization organization = new Organization(1L,"Google");
        Mockito.when(organizationService.deleteOrganizationById(1L))
                .thenReturn(organization);
        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/organizations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Google")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void should_return_not_found_if_delete_non_exist_organization() throws Exception {
        // given
        Mockito.when(organizationService.deleteOrganizationById(1L))
                .thenThrow(new NoSuchElementException("Can't find organization"));
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/organizations/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", equalTo("Can't find organization")))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void should_update_organization_by_given_argument_in_controller() throws Exception {
        // given

        Mockito.when(organizationService.editOrganization(Mockito.any()))
                .thenReturn(new Organization(1L, "Amazon"));
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"Amazon\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("Amazon")))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void when_send_update_request_should_throw_exception_for_not_existing_organization() throws Exception {
        // given
        Mockito.when(organizationService.editOrganization(Mockito.any()))
                .thenThrow(new NoSuchElementException("Can't find element to update"));
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/organizations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"name\": \"Amazon\"\n" +
                        "}"));
    }

    @Test
    void should_not_valid_organizations_throw_validation_error() throws Exception {
        // given
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"G\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                                equalTo("size must be between 2 and 20")));
        Mockito.verifyNoMoreInteractions(organizationService);

    }

}