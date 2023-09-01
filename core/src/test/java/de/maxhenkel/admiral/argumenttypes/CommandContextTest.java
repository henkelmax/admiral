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
import static org.mockito.Mockito.*;

public class CommandContextTest {

    @Test
    @DisplayName("Context injection")
    public void contextInjection() throws CommandSyntaxException {
        try (MockedStatic<CommandContextCommands> staticMock = mockStatic(CommandContextCommands.class)) {
            ArgumentCaptor<CommandContext<?>> commandContextCaptor = ArgumentCaptor.forClass(CommandContext.class);
            ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

            when(CommandContextCommands.string(commandContextCaptor.capture(), stringCaptor.capture(), commandContextCaptor.capture())).thenReturn(1);

            TestUtils.executeCommand("context test", CommandContextCommands.class);

            staticMock.verify(() -> CommandContextCommands.string(any(), any(), any()), times(1));

            assertEquals("test", stringCaptor.getValue());
            assertEquals(2, commandContextCaptor.getAllValues().size());
        }
    }

    private static class CommandContextCommands {
        @Command("context")
        public static int string(CommandContext<?> context1, String string, CommandContext<?> context2) {
            return 1;
        }
    }

}
