package com.example.conferenceroomreservationsystem.conference_room;

import com.example.conferenceroomreservationsystem.ConferenceRoomReservationSystemApplication;
import com.example.conferenceroomreservationsystem.conferenceRoom.ConferenceRoomRepository;
import com.example.conferenceroomreservationsystem.organization.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ConferenceRoomReservationSystemApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ConferenceRoomIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    OrganizationRepository organizationRepository;

//    @Test
//    void when_send_post_request_to_add_conference_room_then_conference_room_should_be_added() {
//        Uzupełnij z gitHuba trenera
//    }

}
