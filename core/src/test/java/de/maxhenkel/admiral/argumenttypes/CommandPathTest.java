package de.maxhenkel.admiral.argumenttypes;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.TestUtils;
import de.maxhenkel.admiral.annotations.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

public class CommandPathTest {

    @Test
    @DisplayName("Unannotated method")
    public void unannotatedMethod() {
        try (MockedStatic<CommandPathCommands> staticMock = mockStatic(CommandPathCommands.class)) {
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

            when(CommandPathCommands.string(any(), stringCaptor.capture())).thenReturn(1);

            assertThrowsExactly(CommandSyntaxException.class, () -> TestUtils.executeCommand("test string", CommandPathCommands.class));

            staticMock.verify(() -> CommandPathCommands.string(any(), any()), times(0));
        }
    }

    @Test
    @DisplayName("Annotated method")
    public void annotatedMethod() throws CommandSyntaxException {
        try (MockedStatic<CommandPathCommands2> staticMock = mockStatic(CommandPathCommands2.class)) {
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

            when(CommandPathCommands2.string(stringCaptor.capture())).thenReturn(1);

            TestUtils.executeCommand("test string", CommandPathCommands2.class);

            staticMock.verify(() -> CommandPathCommands2.string(any()), times(1));

            assertEquals("string", stringCaptor.getValue());

            ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(int.class);

            when(CommandPathCommands2.integer(intCaptor.capture())).thenReturn(1);

            TestUtils.executeCommand("test int 8", CommandPathCommands2.class);

            assertEquals(8, intCaptor.getValue());
        }
    }

    @Command("test")
    private static class CommandPathCommands {

        public static int string(CommandContext<?> context, String string) {
            return 1;
        }

    }

    @Command("test")
    private static class CommandPathCommands2 {

        @Command
        public static int string(String string) {
            return 1;
        }

        @Command("int")
        public static int integer(int integer) {
            return 1;
        }

    }

}
