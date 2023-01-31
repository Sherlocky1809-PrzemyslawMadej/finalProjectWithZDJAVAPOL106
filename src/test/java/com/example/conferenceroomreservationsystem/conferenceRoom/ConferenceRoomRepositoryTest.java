package com.example.conferenceroomreservationsystem.conferenceRoom;

import com.example.conferenceroomreservationsystem.organization.FindByNameArgumentProvider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ConferenceRoomRepositoryTest {

    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @ParameterizedTest
    @ArgumentsSource(FindByNameRoomsArgumentProvider.class)
    void should_return_conference_room_or_not_by_given_name(List<ConferenceRoom> conferenceRoomList,
                                                            String crNameToFind,
                                                            boolean exists) {
        // given
        conferenceRoomList.forEach(o -> testEntityManager.persist(o));
        // when
        Optional<ConferenceRoom> result = conferenceRoomRepository.findByName(crNameToFind);
        // then
        assertEquals(exists, result.isPresent());

    }

    @ParameterizedTest
    @ArgumentsSource(SortByNameRoomsArgumentsProvider.class)
    void should_return_list_of_conference_rooms_with_sorting(
            List<ConferenceRoom> conferenceRoomList,
            Sort sortBy,
            List<ConferenceRoom> sortedConferenceRoomList) {

        // given
        conferenceRoomList.forEach(o -> testEntityManager.persist(o));
        // when
        List<ConferenceRoom> results = conferenceRoomRepository.findAll(sortBy);
        // then
        for(int i = 0; i < sortedConferenceRoomList.size(); i++) {
            assertEquals(sortedConferenceRoomList.get(i).getName(),
                    results.get(i).getName());

        }

    }

}