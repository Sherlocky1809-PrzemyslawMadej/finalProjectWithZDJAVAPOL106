package com.example.conferenceroomreservationsystem.conferenceRoom;

import com.example.conferenceroomreservationsystem.organization.Organization;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ConferenceRoom {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    @Size(min = 2, max = 20)
    @NotBlank
    private String name;

    @Pattern(regexp = "^\\d\\.\\d{2}$")
    private String identifier;

    @Min(0)
    @Max(10)
    @NotNull
    private Integer floor;

    @NotNull
    private Boolean availability;

    @Min(10)
    @Max(50)
    private Integer numberOfSeats;

    @ManyToOne
    private Organization organization;

    public ConferenceRoom() {
    }

    public ConferenceRoom(String name, Integer floor, Boolean availability) {
        this.name = name;
        this.floor = floor;
        this.availability = availability;
    }

    public ConferenceRoom(String name, String identifier, Integer floor, Boolean availability,
                          Integer numberOfSeats, Organization organization) {
        this.name = name;
        this.identifier = identifier;
        this.floor = floor;
        this.availability = availability;
        this.numberOfSeats = numberOfSeats;
        this.organization = organization;
    }

    public ConferenceRoom(String id, String name, String identifier, Integer floor,
                          Boolean availability, Integer numberOfSeats, Organization organization) {
        this.id = id;
        this.name = name;
        this.identifier = identifier;
        this.floor = floor;
        this.availability = availability;
        this.numberOfSeats = numberOfSeats;
        this.organization = organization;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConferenceRoom that = (ConferenceRoom) o;
        return availability == that.availability && numberOfSeats == that.numberOfSeats && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(identifier, that.identifier) && Objects.equals(floor, that.floor) && Objects.equals(organization, that.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, identifier, floor, availability, numberOfSeats, organization);
    }

    @Override
    public String toString() {
        return "ConferenceRoom{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", identifier='" + identifier + '\'' +
                ", floor=" + floor +
                ", availability=" + availability +
                ", numberOfSeats=" + numberOfSeats +
                ", organization=" + organization +
                '}';
    }
}
