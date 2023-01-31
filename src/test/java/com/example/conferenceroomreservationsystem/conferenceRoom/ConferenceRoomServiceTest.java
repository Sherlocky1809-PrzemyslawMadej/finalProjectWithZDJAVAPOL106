package com.example.conferenceroomreservationsystem.conferenceRoom;

import com.example.conferenceroomreservationsystem.SortType;
import com.example.conferenceroomreservationsystem.organization.Organization;
import com.example.conferenceroomreservationsystem.organization.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ConferenceRoomServiceTest {

    @TestConfiguration
    static class ConferenceRoomServiceTestConfiguration {
        @Bean
        public ConferenceRoomService conferenceRoomService(ConferenceRoomRepository roomRepository,
                                                           OrganizationRepository organizationRepository) {
            return new ConferenceRoomService(roomRepository, organizationRepository);
        }
    }

    @MockBean
    ConferenceRoomRepository conferenceRoomRepository;

    @MockBean
    OrganizationRepository organizationRepository;

    @Autowired
    ConferenceRoomService conferenceRoomService;

    @Test
    void should_add_cr_if_repository_not_contain_room_by_given_name() {
        // given
        Organization organizationBookingAddedConferenceRoom = new Organization(1L, "Vesbo");
        String name = "Blue";
        ConferenceRoom conferenceRoomToAdd = new ConferenceRoom(name, "1.23",
                4, true, 30, organizationBookingAddedConferenceRoom);
        Mockito.when(organizationRepository.findById(1L))
                .thenReturn(Optional.of(organizationBookingAddedConferenceRoom));
        Mockito.when(conferenceRoomRepository.findByName(name)).thenReturn(Optional.empty());
        // when
        conferenceRoomService.addConferenceRoom(conferenceRoomToAdd);
        // then
        Mockito.verify(conferenceRoomRepository).save(conferenceRoomToAdd);
    }

    @Test
    void should_not_add_cr_if_repository_contain_room_by_given_name() {
        // given
        Organization organizationBookingAddedConferenceRoom = new Organization(1L, "Vesbo");
        String name = "Blue";
        ConferenceRoom conferenceRoomToAdd = new ConferenceRoom(name, "1.23",
                4, true, 30, organizationBookingAddedConferenceRoom);
        Mockito.when(organizationRepository.findById(1L))
                .thenReturn(Optional.of(organizationBookingAddedConferenceRoom));
        Mockito.when(conferenceRoomRepository.findByName(name)).thenReturn(Optional.of(conferenceRoomToAdd));
        // when & then
        assertThatThrownBy(() -> conferenceRoomService.addConferenceRoom(conferenceRoomToAdd))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Conference room by given name is already exists!");

    }

    @Test
    void should_get_cr_list() {
        // given
        // when
        conferenceRoomService.getConferenceRoomList();
        // then
        Mockito.verify(conferenceRoomRepository).findAll();

    }

    @Test
    void should_edit_cr_if_repository_contain_room_by_given_name() {
        // given
        Organization vesbo = new Organization(1L, "Vesbo");
        Organization benz = new Organization(2L, "Benz");
        String name = "Red";
        String newName = "Blue";
        ConferenceRoom oldConferenceRoom = new ConferenceRoom("x", name, "1.23",
                4, true, 30, vesbo);
        ConferenceRoom updatedConferenceRoom = new ConferenceRoom( "x", newName, "1.25",
                5, false, 40, benz);
        Mockito.when(organizationRepository.findById(1L))
                .thenReturn(Optional.of(vesbo));
        Mockito.when(organizationRepository.findById(2L))
                .thenReturn(Optional.of(benz));
        Mockito.when(conferenceRoomRepository.findById(oldConferenceRoom.getId()))
                .thenReturn(Optional.of(oldConferenceRoom));
        Mockito.when(conferenceRoomRepository.findById(updatedConferenceRoom.getId()))
                .thenReturn(Optional.of(updatedConferenceRoom));
        // when
        conferenceRoomService.editConferenceRoom(updatedConferenceRoom);
        // then
        Mockito.verify(conferenceRoomRepository).save(updatedConferenceRoom);
    }

    @Test
    void should_throw_no_such_element_exception_if_repository_not_contain_room_by_given_id_to_edit() {
        // given
        Organization vesbo = new Organization(1L, "Vesbo");
        String name = "Red";
        ConferenceRoom conferenceRoomToEdit = new ConferenceRoom("x", name, "1.23",
                4, true, 30, vesbo);
        Mockito.when(organizationRepository.findById(1L))
                .thenReturn(Optional.of(vesbo));
        Mockito.when(conferenceRoomRepository.findById("y"))
                .thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> conferenceRoomService.editConferenceRoom(conferenceRoomToEdit))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No conference room to update found!");
    }

    @Test
    void should_delete_cr_by_given_id() {
        // given
        Organization benz = new Organization(1L, "Benz");
        String name = "Blue";
        ConferenceRoom conferenceRoomToDelete = new ConferenceRoom( "y", name, "1.25",
                5, false, 40, benz);
        Mockito.when(conferenceRoomRepository.findById(conferenceRoomToDelete.getId()))
                .thenReturn(Optional.of(conferenceRoomToDelete));
        // when
        conferenceRoomService.deleteConferenceRoom(conferenceRoomToDelete.getId());
        // then
        Mockito.verify(conferenceRoomRepository).delete(conferenceRoomToDelete);
    }

    @Test
    void should_throw_no_such_element_exception_if_repository_not_contain_room_by_given_id_to_delete() {
        // given
        Organization vesbo = new Organization(1L, "Vesbo");
        String name = "Red";
        ConferenceRoom conferenceRoomToDelete = new ConferenceRoom("x", name, "1.23",
                4, true, 30, vesbo);
        Mockito.when(organizationRepository.findById(1L))
                .thenReturn(Optional.of(vesbo));
        Mockito.when(conferenceRoomRepository.findById("y"))
                .thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> conferenceRoomService.deleteConferenceRoom(conferenceRoomToDelete.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("No conference room to delete found!");
    }

    @Test
    void should_get_cr_by_given_id() {
        // given
        Organization benz = new Organization(1L, "Benz");
        String name = "Blue";
        ConferenceRoom conferenceRoom = new ConferenceRoom( "x", name, "1.25",
                5, false, 40, benz);
        Mockito.when(conferenceRoomRepository.findById(conferenceRoom.getId()))
                .thenReturn(Optional.of(conferenceRoom));
        // when
        conferenceRoomService.getConferenceRoomById(conferenceRoom.getId());
        // then
        Mockito.verify(conferenceRoomRepository).findById(conferenceRoom.getId());
    }

    @Test
    void should_throw_no_such_element_exception_if_repository_not_get_room_by_given_id() {
        // given
        Organization vesbo = new Organization(1L, "Vesbo");
        String name = "Red";
        ConferenceRoom conferenceRoom = new ConferenceRoom("x", name, "1.23",
                4, true, 30, vesbo);
        Mockito.when(organizationRepository.findById(1L))
                .thenReturn(Optional.of(vesbo));
        Mockito.when(conferenceRoomRepository.findById("y"))
                .thenReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> conferenceRoomService.getConferenceRoomById(conferenceRoom.getId()))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Not found conferenceRoom by given id!");
    }

    @ParameterizedTest
    @ArgumentsSource(SortByNameRoomsArgumentsProvider.class)
    void should_sort_list_in_proper_direction(
            List<ConferenceRoom> conferenceRoomList,
            SortType sortBy,
            List<ConferenceRoom> sortedConferenceRoomList
    ) {
        // when
        List<ConferenceRoom> resultList = conferenceRoomService.sortListOfConferenceRooms(conferenceRoomList,
                sortBy);
        // then
        assertEquals(sortedConferenceRoomList, resultList);
    }

    @Test
    void should_return_cr_list_in_asc_order_booked_by_given_organization() {
        // given
        String name = "Red";
        String name2 = "Blue";
        ConferenceRoom conferenceRoom1 = new ConferenceRoom(name,
                4, true);
        ConferenceRoom conferenceRoom2 = new ConferenceRoom(name2,
                5, true);
        Organization vesbo = new Organization(1L, "Vesbo", List.of(conferenceRoom1, conferenceRoom2));
        Mockito.when(organizationRepository.findById(1L))
                .thenReturn(Optional.of(vesbo));
        Mockito.when(conferenceRoomRepository.findById(conferenceRoom1.getId()))
                .thenReturn(Optional.of(conferenceRoom1));
        Mockito.when(conferenceRoomRepository.findById(conferenceRoom2.getId()))
                .thenReturn(Optional.of(conferenceRoom2));
        List<ConferenceRoom> expectedListAsc = List.of(conferenceRoom2, conferenceRoom1);
        // when
        List<ConferenceRoom> result = conferenceRoomService.getConferenceRoomsByGivenOrganization("Vesbo",
                SortType.ASC);
        // then
        Mockito.verify(organizationRepository).findByName(vesbo.getName());
        assertEquals(expectedListAsc, result);
    }
}