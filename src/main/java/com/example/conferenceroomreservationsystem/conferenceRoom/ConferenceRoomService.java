package com.example.conferenceroomreservationsystem.conferenceRoom;

import com.example.conferenceroomreservationsystem.SortType;
import com.example.conferenceroomreservationsystem.organization.Organization;
import com.example.conferenceroomreservationsystem.organization.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        conferenceRoomRepository.findByName(conferenceRoom.getName())
                .ifPresent(o -> {
                    throw new IllegalArgumentException("Conference room by given name is already exists!");
                });
        if (conferenceRoom.getName() != null) {
            conferenceRoom.setName(conferenceRoom.getName());
        }

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

    ConferenceRoom getConferenceRoomById(String id) {
        return conferenceRoomRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Not found conferenceRoom by given id!"));
    }

    List<ConferenceRoom> getConferenceRoomsByGivenOrganization(Organization organization, SortType sortType) {
        List<ConferenceRoom> conferenceRoomsListByOrganization = organization.getBookedRooms();

        return sortListOfConferenceRooms(conferenceRoomsListByOrganization, sortType);
    }

    List<ConferenceRoom> getConferenceRoomsByAvailability(SortType sortType) {
        List<ConferenceRoom> allConferenceRooms = getConferenceRoomList();
        for (ConferenceRoom cr : allConferenceRooms) {
            cr.setAvailability(cr.getOrganization() == null);
        }
                List<ConferenceRoom> filteredConferenceRooms = allConferenceRooms.stream()
                .filter(ConferenceRoom::getAvailability)
                .collect(Collectors.toList());
        return sortListOfConferenceRooms(filteredConferenceRooms, sortType);
    }

    List<ConferenceRoom> getConferenceRoomsByGivenNumbersOfSeats(Integer seats, SortType sortType) {
        List<ConferenceRoom> allConferenceRooms = getConferenceRoomList();
        List<ConferenceRoom> filteredConferenceRoomsBySeats = allConferenceRooms.stream()
                .filter(cr -> cr.getNumberOfSeats() >= seats)
                .collect(Collectors.toList());
        return sortListOfConferenceRooms(filteredConferenceRoomsBySeats, sortType);
    }

    private static List<ConferenceRoom> sortListOfConferenceRooms(List<ConferenceRoom> conferenceRoomsListToSort,
                                                                  SortType sortType) {
        if(sortType == SortType.ASC) {
            return conferenceRoomsListToSort.stream()
                    .sorted(Comparator.comparing(ConferenceRoom::getName))
                    .collect(Collectors.toList());
        } else if (sortType == SortType.DESC) {
            return conferenceRoomsListToSort.stream()
                    .sorted(Comparator.comparing(ConferenceRoom::getName).reversed())
                    .collect(Collectors.toList());
        } else {
            return conferenceRoomsListToSort;
        }
    }
}
