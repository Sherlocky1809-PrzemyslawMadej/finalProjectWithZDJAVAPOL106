package com.example.conferenceroomreservationsystem.conferenceRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, String> {

    Optional<ConferenceRoom> findByName(String name);

}
