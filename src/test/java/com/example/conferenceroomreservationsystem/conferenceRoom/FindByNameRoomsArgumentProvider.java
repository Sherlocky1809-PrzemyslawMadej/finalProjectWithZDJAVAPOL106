package com.example.conferenceroomreservationsystem.conferenceRoom;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FindByNameRoomsArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        "Blue",
                        false
                ),
                Arguments.of(
                        Arrays.asList(new ConferenceRoom("Red", 3, true),
                                new ConferenceRoom("Blue", 4, true),
                                new ConferenceRoom("White", 5, true)),
                        "Red",
                        true
                ),
                Arguments.of(Arrays.asList(new ConferenceRoom("Red", 3, true),
                        new ConferenceRoom("Blue", 4, true),
                        new ConferenceRoom("White", 5, true)),
                        "Black",
                        false
                )
        );
    }
}
