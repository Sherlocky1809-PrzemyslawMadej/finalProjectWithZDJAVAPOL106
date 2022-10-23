package com.example.conferenceroomreservationsystem.organization;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

public class FindByNameArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        "test1",
                        false
                ),
                Arguments.of(
                        Arrays.asList(new Organization("test1"),
                                new Organization("test2"),
                                new Organization("test3")
                        ),
                        "test1",
                        true
                ),
                Arguments.of(
                        Arrays.asList(new Organization("test1"),
                                new Organization("test2"),
                                new Organization("test3")
                ),
                "test4",
                false
        )
                );
    }
}
