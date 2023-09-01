package de.maxhenkel.admiral.argumenttypes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.TestUtils;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.RequiresPermissionLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

public class PermissionLevelTest {

    @Test
    @DisplayName("Method permission annotation")
    public void methodPermissionAnnotation() throws CommandSyntaxException {
        try (MockedStatic<PermissionCommands> staticMock = mockStatic(PermissionCommands.class)) {
            assertThrowsExactly(CommandSyntaxException.class, () -> TestUtils.executeCommand("permission_annotation", PermissionCommands.class, 1));
            staticMock.verify(PermissionCommands::permissionAnnotation, never());

            TestUtils.executeCommand("permission_annotation", PermissionCommands.class, 2);
            staticMock.verify(PermissionCommands::permissionAnnotation, times(1));
        }
    }

    @Test
    @DisplayName("Class permission annotation")
    public void classPermissionAnnotation() throws CommandSyntaxException {
        try (MockedStatic<PermissionCommands> staticMock = mockStatic(PermissionCommands.class)) {
            assertThrowsExactly(CommandSyntaxException.class, () -> TestUtils.executeCommand("no_permission_annotation", PermissionCommands.class, 0));
            staticMock.verify(PermissionCommands::noPermissionAnnotation, never());

            TestUtils.executeCommand("no_permission_annotation", PermissionCommands.class, 1);
            staticMock.verify(PermissionCommands::noPermissionAnnotation, times(1));
        }
    }

    @RequiresPermissionLevel(1)
    private static class PermissionCommands {
        @RequiresPermissionLevel(2)
        @Command("permission_annotation")
        public static int permissionAnnotation() {
            return 1;
        }

        @Command("no_permission_annotation")
        public static int noPermissionAnnotation() {
            return 1;
        }
    }

}
