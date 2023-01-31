package com.example.conferenceroomreservationsystem.conferenceRoom;

import com.example.conferenceroomreservationsystem.SortType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/rooms")
public class ConferenceRoomController {

    private final ConferenceRoomService conferenceRoomService;

    ConferenceRoomController(ConferenceRoomService conferenceRoomService) {
        this.conferenceRoomService = conferenceRoomService;
    }

    @GetMapping
    List<ConferenceRoom> getAllConferenceRooms() {
    return conferenceRoomService.getConferenceRoomList();
    }

    @PostMapping
    ConferenceRoom addConferenceRoom(@Valid @RequestBody ConferenceRoom conferenceRoom) {
        return conferenceRoomService.addConferenceRoom(conferenceRoom);
    }

    @PutMapping
    ConferenceRoom updateConferenceRoom(@RequestBody ConferenceRoom conferenceRoom) {
        return conferenceRoomService.editConferenceRoom(conferenceRoom);
    }

    @DeleteMapping("/{id}")
    ConferenceRoom deleteConferenceRoom(@PathVariable String id) {
        return conferenceRoomService.deleteConferenceRoom(id);
    }

    @GetMapping("/{id}")
    ConferenceRoom getConferenceRoomById(@PathVariable String id) {
        return conferenceRoomService.getConferenceRoomById(id);
    }

    @GetMapping("/{name}/{sortType}")
    List<ConferenceRoom> getConferenceRoomListByOrganizationName(@PathVariable String name,
                                                                  @PathVariable SortType sortType) {
        return conferenceRoomService.getConferenceRoomsByGivenOrganization(name, sortType);
    }

    @GetMapping("/{sortType}")
    List<ConferenceRoom> getConferenceRoomListSortedByAvailability(@PathVariable SortType sortType) {
        return conferenceRoomService.getConferenceRoomsByAvailability(sortType);
    }

    @GetMapping("/{numberOfSeats}/{sortType}")
    List<ConferenceRoom> getConferenceRoomListByFulfillRequireNumberOfSeats(@PathVariable Integer numberOfSeats,
                                                                            @PathVariable SortType sortType) {
        return conferenceRoomService.getConferenceRoomsByGivenNumbersOfSeats(numberOfSeats, sortType);
    }
}
