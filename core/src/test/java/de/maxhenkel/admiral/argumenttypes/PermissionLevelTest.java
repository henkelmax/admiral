package de.maxhenkel.admiral.argumenttypes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.TestUtils;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.Name;
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

    @Test
    @DisplayName("Argument permission")
    public void argumentPermission() throws CommandSyntaxException {
        try (MockedStatic<ArgumentPermissionCommands> staticMock = mockStatic(ArgumentPermissionCommands.class)) {
            assertThrowsExactly(CommandSyntaxException.class, () -> TestUtils.executeCommand("permission_annotation1 1", ArgumentPermissionCommands.class, 0));
            staticMock.verify(() -> ArgumentPermissionCommands.permissionAnnotation1(1), never());

            TestUtils.executeCommand("no_permission_annotation1", ArgumentPermissionCommands.class, 0);
            staticMock.verify(ArgumentPermissionCommands::noPermissionAnnotation1, times(1));

            assertThrowsExactly(CommandSyntaxException.class, () -> TestUtils.executeCommand("permission_annotation2", ArgumentPermissionCommands.class, 0));
            staticMock.verify(ArgumentPermissionCommands::permissionAnnotation2, never());

            TestUtils.executeCommand("no_permission_annotation2 1", ArgumentPermissionCommands.class, 0);
            staticMock.verify(() -> ArgumentPermissionCommands.noPermissionAnnotation2(1), times(1));
        }
    }

    @Test
    @DisplayName("Parameter permission overloading")
    public void permissionOverloading() throws CommandSyntaxException {
        try (MockedStatic<ArgumentPermissionOverloadingCommands> staticMock = mockStatic(ArgumentPermissionOverloadingCommands.class)) {
            TestUtils.executeCommand("command 123", ArgumentPermissionOverloadingCommands.class, 0);
            staticMock.verify(() -> ArgumentPermissionOverloadingCommands.cammand2(123), times(1));

            assertThrowsExactly(CommandSyntaxException.class, () -> TestUtils.executeCommand("command test", ArgumentPermissionOverloadingCommands.class, 0));
            staticMock.verify(() -> ArgumentPermissionOverloadingCommands.cammand1(any()), never());

            TestUtils.executeCommand("command test", ArgumentPermissionOverloadingCommands.class, 1);
            staticMock.verify(() -> ArgumentPermissionOverloadingCommands.cammand1("test"), times(1));
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

    private static class ArgumentPermissionCommands {
        @RequiresPermissionLevel(1)
        @Command("permission_annotation1")
        public static int permissionAnnotation1(int value) {
            return 1;
        }

        @Command("no_permission_annotation1")
        public static int noPermissionAnnotation1() {
            return 1;
        }

        @RequiresPermissionLevel(1)
        @Command("permission_annotation2")
        public static int permissionAnnotation2() {
            return 1;
        }

        @Command("no_permission_annotation2")
        public static int noPermissionAnnotation2(int value) {
            return 1;
        }
    }

    private static class ArgumentPermissionOverloadingCommands {
        @RequiresPermissionLevel(1)
        @Command("command")
        public static int cammand1(@Name("string") String value) {
            return 1;
        }

        @Command("command")
        public static int cammand2(@Name("int") int value) {
            return 1;
        }

    }

}
