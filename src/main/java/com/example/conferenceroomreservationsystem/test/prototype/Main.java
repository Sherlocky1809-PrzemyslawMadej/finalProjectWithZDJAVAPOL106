package com.example.conferenceroomreservationsystem.test.prototype;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        FootballPlayer footballPlayer = new FootballPlayer("Cristiano", "Ronaldo", 34, Position.FORWARDER);
        FootballTeam footballTeam = new FootballTeam("Manchaster Unites",Arrays.asList(footballPlayer), 2000);
        FootballLeague footballLeague = new FootballLeague("Premier League", Arrays.asList(footballTeam), "England");

        try {
            FootballLeague footballLeagueCopy = (FootballLeague) footballLeague.clone();
            System.out.println("Football league: " + footballLeague);
            System.out.println("Football league original: " + footballLeagueCopy);

        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
