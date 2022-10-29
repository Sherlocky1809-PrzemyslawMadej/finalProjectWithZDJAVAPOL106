package com.example.conferenceroomreservationsystem.organization;

import com.example.conferenceroomreservationsystem.ConferenceRoomReservationSystemApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ConferenceRoomReservationSystemApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class OrganizationIntegrationTest {

    // 1. Próba dodania zduplikowanego rekordu
//    2. Próba update istniejącego rekordu
//    3. Usunięcie instniejącego rekordu
//    4. Usunięcie nieistniejącego rekordu
//    5. Sortowanie

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrganizationRepository organizationRepository;

    @Test
    void when_get_organization_which_exists_in_db_then_it_should_be_returned_to_response() throws Exception {
        // given
        organizationRepository.save(new Organization(1L, "test"));
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations/test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", equalTo("test")));
    }

    @Test
    void when_add_duplicated_organization_in_db_it_will_return_bad_request() throws Exception {
        // given
        organizationRepository.save(new Organization(1L, "test"));
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"test\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void when_update_organization_in_db_it_will_return_bad_request() throws Exception {
        // given
        organizationRepository.save(new Organization(1L, "test"));
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"test\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void when_delete_not_founded_organization_in_db_it_will_return_not_found() throws Exception {
        // given
        organizationRepository.deleteAll();
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/organizations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void when_delete_founded_organization_in_db_it_will_return_OK() throws Exception {
        // given
        organizationRepository.save(new Organization(2L, "test"));
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/organizations/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void when_get_sorted_asc_organization_in_db_will_return_OK() throws Exception {
        // given
        organizationRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
