package de.maxhenkel.admiral.argumenttypes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.TestUtils;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StringTest {

    @Test
    @DisplayName("String argument type")
    public void stringArgumentType() throws CommandSyntaxException {
        testInputOutput("string string test", "test");
    }

    @Test
    @DisplayName("Quoted string")
    public void quotedString() throws CommandSyntaxException {
        testInputOutput("string string \"test\"", "test");
    }

    @Test
    @DisplayName("Quoted string with spaces")
    public void quotedStringWithSpaces() throws CommandSyntaxException {
        testInputOutput("string string \"test 123\"", "test 123");
    }

    private void testInputOutput(String command, String expected) throws CommandSyntaxException {
        MockedStatic<StringCommands> staticMock = mockStatic(StringCommands.class);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        when(StringCommands.string(stringCaptor.capture())).thenReturn(1);
        TestUtils.executeCommand(command, StringCommands.class);
        staticMock.verify(() -> StringCommands.string(any()), times(1));
        assertEquals(expected, stringCaptor.getValue());
        staticMock.close();
    }

    @Test
    @DisplayName("Optional string present")
    public void optionalStringPresent() throws CommandSyntaxException {
        testInputOutputOptional("string optional_string test", "test");
    }

    @Test
    @DisplayName("Optional string not present")
    public void optionalStringNotPresent() throws CommandSyntaxException {
        testInputOutputOptional("string optional_string", null);
    }

    private void testInputOutputOptional(String command, String expected) throws CommandSyntaxException {
        MockedStatic<StringCommands> staticMock = mockStatic(StringCommands.class);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        when(StringCommands.optionalString(stringCaptor.capture())).thenReturn(1);
        TestUtils.executeCommand(command, StringCommands.class);
        staticMock.verify(() -> StringCommands.optionalString(any()), times(1));
        assertEquals(expected, stringCaptor.getValue());
        staticMock.close();
    }

    @Test
    @DisplayName("Java optional string present")
    public void javaOptionalStringPresent() throws CommandSyntaxException {
        testInputOutputJavaOptional("string java_optional_string test", Optional.of("test"));
    }

    @Test
    @DisplayName("Java optional string not present")
    public void javaOptionalStringNotPresent() throws CommandSyntaxException {
        testInputOutputJavaOptional("string java_optional_string", Optional.empty());
    }

    private void testInputOutputJavaOptional(String command, Optional<String> expected) throws CommandSyntaxException {
        MockedStatic<StringCommands> staticMock = mockStatic(StringCommands.class);
        ArgumentCaptor<Optional<String>> stringCaptor = ArgumentCaptor.forClass(Optional.class);
        when(StringCommands.javaOptionalString(stringCaptor.capture())).thenReturn(1);
        TestUtils.executeCommand(command, StringCommands.class);
        staticMock.verify(() -> StringCommands.javaOptionalString(any()), times(1));
        assertEquals(expected, stringCaptor.getValue());
        staticMock.close();
    }

    @Command("string")
    private static class StringCommands {
        @Command("string")
        public static int string(String string) {
            return 1;
        }

        @Command("optional_string")
        public static int optionalString(@OptionalArgument String string) {
            return 1;
        }

        @Command("java_optional_string")
        public static int javaOptionalString(Optional<String> string) {
            return 1;
        }
    }

}
