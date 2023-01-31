package com.example.conferenceroomreservationsystem.test.prototype;

import java.util.List;
import java.util.stream.Collectors;

public class FootballTeam implements Cloneable {

    private String name;
    private List<FootballPlayer> playerList;
    private int yearOfEstablish;

    public FootballTeam(String name, List<FootballPlayer> playerList, int yearOfEstablish) {
        this.name = name;
        this.playerList = playerList;
        this.yearOfEstablish = yearOfEstablish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FootballPlayer> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<FootballPlayer> playerList) {
        this.playerList = playerList;
    }

    public int getYearOfEstablish() {
        return yearOfEstablish;
    }

    public void setYearOfEstablish(int yearOfEstablish) {
        this.yearOfEstablish = yearOfEstablish;
    }

    @Override
    public String toString() {
        return "FootballTeam{" +
                "name='" + name + '\'' +
                ", playerList=" + playerList +
                ", yearOfEstablish=" + yearOfEstablish +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        FootballTeam footballTeamCopy = (FootballTeam) super.clone();
        List<FootballPlayer> footballPlayerListCopy = footballTeamCopy.getPlayerList().stream().map(p->{
            try {
                return (FootballPlayer) p.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        footballTeamCopy.setPlayerList(footballPlayerListCopy);
        return footballTeamCopy;
    }
}
