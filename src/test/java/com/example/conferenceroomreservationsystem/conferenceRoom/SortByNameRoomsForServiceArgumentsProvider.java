package com.example.conferenceroomreservationsystem.conferenceRoom;

import com.example.conferenceroomreservationsystem.SortType;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public class SortByNameRoomsForServiceArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        SortType.ASC,
                        Collections.emptyList()
                ),
                Arguments.of(
                        Arrays.asList(
                                new ConferenceRoom("Red", 4, true),
                                new ConferenceRoom("Blue", 3, true),
                                new ConferenceRoom("White", 6, true)
                        ),
                        SortType.ASC,
                        Arrays.asList(
                                new ConferenceRoom("Blue", 3, true),
                                new ConferenceRoom("Red", 4, true),
                                new ConferenceRoom("White", 6, true)
                        )
                ),
                Arguments.of(
                        Arrays.asList(
                                new ConferenceRoom("Red", 4, true),
                                new ConferenceRoom("Blue", 3, true),
                                new ConferenceRoom("White", 6, true)
                        ),
                        SortType.DESC,
                        Arrays.asList(
                                new ConferenceRoom("White", 6, true),
                                new ConferenceRoom("Red", 4, true),
                                new ConferenceRoom("Blue", 3, true)
                        )
                )
        );
    }
}
