package com.example.conferenceroomreservationsystem.organization;

import com.example.conferenceroomreservationsystem.conferenceRoom.ConferenceRoom;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Can't be null or blank!")
    @Size(min = 2, max = 20)
    private String name;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy = "organization")
//    private List<ConferenceRoom> bookedRooms;

    public Organization() {
    }

    public Organization(String name) {
        this.name = name;
    }

    public Organization(Long id, String name) {
        this.id = id;
        this.name = name;
    }

//    public Organization(Long id, String name, List<ConferenceRoom> bookedRooms) {
//        this.id = id;
//        this.name = name;
//        this.bookedRooms = bookedRooms;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<ConferenceRoom> getBookedRooms() {
//        return bookedRooms;
//    }
//
//    public void setBookedRooms(List<ConferenceRoom> bookedRooms) {
//        this.bookedRooms = bookedRooms;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
