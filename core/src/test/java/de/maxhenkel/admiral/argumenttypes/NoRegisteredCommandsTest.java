package de.maxhenkel.admiral.argumenttypes;

import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.admiral.Admiral;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class NoRegisteredCommandsTest {

    @Test
    @DisplayName("No registered Commands")
    public void noRegisteredCommands() {
        assertThrowsExactly(IllegalStateException.class, () -> {
            Admiral.builder(new CommandDispatcher<>()).addCommandClasses(NoCommandClass.class).build();
        });
    }

    public static class NoCommandClass {

    }

}
