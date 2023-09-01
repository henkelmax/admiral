package de.maxhenkel.admiral.argumenttypes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.TestUtils;
import de.maxhenkel.admiral.annotations.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

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

    @Command("string")
    private static class StringCommands {
        @Command("string")
        public static int string(String string) {
            return 1;
        }
    }

}
