package de.maxhenkel.admiral.argumenttypes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.TestUtils;
import de.maxhenkel.admiral.annotations.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OptionalTest {

    @Test
    @DisplayName("Not present optional int")
    public void notPresentOptionalInt() throws CommandSyntaxException {
        try (MockedStatic<OptionalCommands> staticMock = mockStatic(OptionalCommands.class)) {
            ArgumentCaptor<Optional<Integer>> optionalIntCaptor = ArgumentCaptor.forClass(Optional.class);
            when(OptionalCommands.optionalInt(optionalIntCaptor.capture())).thenReturn(1);
            TestUtils.executeCommand("optional int", OptionalCommands.class);
            staticMock.verify(() -> OptionalCommands.optionalInt(any()), times(1));
            assertEquals(123, optionalIntCaptor.getValue().orElse(123));
        }
    }

    @Test
    @DisplayName("Present optional int")
    public void presentOptionalInt() throws CommandSyntaxException {
        try (MockedStatic<OptionalCommands> staticMock = mockStatic(OptionalCommands.class)) {
            ArgumentCaptor<Optional<Integer>> optionalIntCaptor = ArgumentCaptor.forClass(Optional.class);
            when(OptionalCommands.optionalInt(optionalIntCaptor.capture())).thenReturn(1);
            TestUtils.executeCommand("optional int 123", OptionalCommands.class);
            staticMock.verify(() -> OptionalCommands.optionalInt(any()), times(1));
            assertEquals(123, optionalIntCaptor.getValue().get());
        }
    }

    @Test
    @DisplayName("Not present optional double")
    public void notPresentOptionalDouble() throws CommandSyntaxException {
        try (MockedStatic<OptionalCommands> staticMock = mockStatic(OptionalCommands.class)) {
            ArgumentCaptor<Optional<Double>> optionalIntCaptor = ArgumentCaptor.forClass(Optional.class);
            when(OptionalCommands.optionalDouble(optionalIntCaptor.capture())).thenReturn(1);
            TestUtils.executeCommand("optional double", OptionalCommands.class);
            staticMock.verify(() -> OptionalCommands.optionalDouble(any()), times(1));
            assertEquals(false, optionalIntCaptor.getValue().isPresent());
        }
    }

    @Test
    @DisplayName("Not present optional string")
    public void notPresentOptionalString() throws CommandSyntaxException {
        try (MockedStatic<OptionalCommands> staticMock = mockStatic(OptionalCommands.class)) {
            ArgumentCaptor<Optional<String>> optionalIntCaptor = ArgumentCaptor.forClass(Optional.class);
            when(OptionalCommands.optional(optionalIntCaptor.capture())).thenReturn(1);
            TestUtils.executeCommand("optional", OptionalCommands.class);
            staticMock.verify(() -> OptionalCommands.optional(any()), times(1));
            assertEquals(false, optionalIntCaptor.getValue().isPresent());
        }
    }

    @Test
    @DisplayName("Present optional string")
    public void presentOptionalString() throws CommandSyntaxException {
        try (MockedStatic<OptionalCommands> staticMock = mockStatic(OptionalCommands.class)) {
            ArgumentCaptor<Optional<String>> optionalIntCaptor = ArgumentCaptor.forClass(Optional.class);
            when(OptionalCommands.optional(optionalIntCaptor.capture())).thenReturn(1);
            TestUtils.executeCommand("optional test", OptionalCommands.class);
            staticMock.verify(() -> OptionalCommands.optional(any()), times(1));
            assertEquals("test", optionalIntCaptor.getValue().get());
        }
    }

    @Command("optional")
    private static class OptionalCommands {
        @Command("int")
        public static int optionalInt(Optional<Integer> integer) {
            return 1;
        }

        @Command("double")
        public static int optionalDouble(Optional<Double> optionalDouble) {
            return 1;
        }

        @Command
        public static int optional(Optional<String> optionalString) {
            return 1;
        }
    }

}
