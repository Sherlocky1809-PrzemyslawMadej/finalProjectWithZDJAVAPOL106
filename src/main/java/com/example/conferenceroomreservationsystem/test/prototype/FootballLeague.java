package com.example.conferenceroomreservationsystem.test.prototype;

/*
Na podstawie poniższej struktury:
FootballLeague -> FootballTeam -> FootballPlayer zaprezentuj działanie mechanizmu klonowania w javie: deepcopy i shallowcopy

class FootballLeague: nazwa, lista zespołów, kraj
class FootballTeam: nazwa, lista zawodników, rok powstania
class FootballPlayer: imię, nazwisko, wiek, pozycja na boisku (enum)

 */

import java.util.List;
import java.util.stream.Collectors;

public class FootballLeague implements Cloneable {

    private String name;
    private List<FootballTeam> footballTeamList;
    private String country;

    public FootballLeague(String name, List<FootballTeam> footballTeamList, String country) {
        this.name = name;
        this.footballTeamList = footballTeamList;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FootballTeam> getFootballTeamList() {
        return footballTeamList;
    }

    public void setFootballTeamList(List<FootballTeam> footballTeamList) {
        this.footballTeamList = footballTeamList;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "FootballLeague{" +
                "name='" + name + '\'' +
                ", footballTeamList=" + footballTeamList +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        FootballLeague footballLeagueCopy = (FootballLeague) super.clone();
        List<FootballTeam> footballTeamsCopy = footballLeagueCopy.footballTeamList.stream()
                .map( ft -> {
                   try {
                       return (FootballTeam) ft.clone();
                   } catch (CloneNotSupportedException ex) {
                       throw new RuntimeException(ex);
                   }
                }).collect(Collectors.toList());
    footballLeagueCopy.setFootballTeamList(footballTeamsCopy);
    return footballLeagueCopy;
    };
}
