package com.example.conferenceroomreservationsystem.conferenceRoom;

import com.example.conferenceroomreservationsystem.SortType;
import com.example.conferenceroomreservationsystem.organization.Organization;
import com.example.conferenceroomreservationsystem.organization.OrganizationRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ConferenceRoomService {

    private final ConferenceRoomRepository conferenceRoomRepository;
    private final OrganizationRepository organizationRepository;

    public ConferenceRoomService(ConferenceRoomRepository conferenceRoomRepository,
                                 OrganizationRepository organizationRepository) {
        this.conferenceRoomRepository = conferenceRoomRepository;
        this.organizationRepository = organizationRepository;
    }

    ConferenceRoom addConferenceRoom(ConferenceRoom conferenceRoom) {
        Organization organization = organizationRepository.findById(conferenceRoom.getOrganization().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Can't find provided organization!"));
        conferenceRoom.setOrganization(organization);
        return conferenceRoomRepository.save(conferenceRoom);
    }

    List<ConferenceRoom> getConferenceRoomList() {
            return conferenceRoomRepository.findAll();
        }

    ConferenceRoom editConferenceRoom(ConferenceRoom conferenceRoom) {

        final String updatedName = conferenceRoom.getName();
        final String updatedIdentifier = conferenceRoom.getIdentifier();
        final Integer updatedNumberOfSeats = conferenceRoom.getNumberOfSeats();
        final Integer updatedFloor = conferenceRoom.getFloor();
        final Boolean updatedAvailability = conferenceRoom.getAvailability();
        final Organization updatedOrganization = conferenceRoom.getOrganization();
        ConferenceRoom conferenceRoomToEdit = conferenceRoomRepository.findById(conferenceRoom.getId())
                .map(cr -> {
                    cr.setName(updatedName != null ? updatedName : cr.getName());
                    cr.setIdentifier(updatedIdentifier != null ? updatedIdentifier : cr.getIdentifier());
                    cr.setNumberOfSeats(updatedNumberOfSeats != null ? updatedNumberOfSeats : cr.getNumberOfSeats());
                    cr.setFloor(updatedFloor != null ? updatedFloor : cr.getFloor());
                    cr.setAvailability(updatedAvailability != null ? updatedAvailability : cr.getAvailability());
                    cr.setOrganization(updatedOrganization != null ? updatedOrganization : cr.getOrganization());

                    return cr;
                })
                .orElseThrow(() -> new NoSuchElementException("No conference room to update found!"));
        return conferenceRoomRepository.save(conferenceRoomToEdit);
    }

    ConferenceRoom deleteConferenceRoom(String id) {
        ConferenceRoom conferenceRoomToDelete = conferenceRoomRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("No conference room to delete found!"));
        conferenceRoomRepository.delete(conferenceRoomToDelete);
        return conferenceRoomToDelete;
    }
}
