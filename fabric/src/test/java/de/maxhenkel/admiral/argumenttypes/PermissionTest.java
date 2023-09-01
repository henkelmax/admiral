package de.maxhenkel.admiral.argumenttypes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.TestUtils;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.RequiresPermission;
import de.maxhenkel.admiral.permissions.PermissionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PermissionTest {

    @Test
    @DisplayName("Method permission annotation")
    public void methodPermissionAnnotation() throws CommandSyntaxException {
        MockedStatic<PermissionCommands> staticMock = mockStatic(PermissionCommands.class);

        TestPermissionManager permissionManager = mock(TestPermissionManager.class);

        ArgumentCaptor<String> permissionCaptor = ArgumentCaptor.forClass(String.class);

        when(permissionManager.hasPermission(any(), permissionCaptor.capture())).thenReturn(true);

        TestUtils.executeCommand("permission_annotation", PermissionCommands.class, permissionManager);

        staticMock.verify(PermissionCommands::permissionAnnotation, times(1));

        assertEquals(2, permissionCaptor.getAllValues().size());
        assertEquals("parent", permissionCaptor.getAllValues().get(0));
        assertEquals("test", permissionCaptor.getAllValues().get(1));

        staticMock.close();
    }

    @Test
    @DisplayName("Class permission annotation")
    public void classPermissionAnnotation() throws CommandSyntaxException {
        MockedStatic<PermissionCommands> staticMock = mockStatic(PermissionCommands.class);

        TestPermissionManager permissionManager = mock(TestPermissionManager.class);

        ArgumentCaptor<String> permissionCaptor = ArgumentCaptor.forClass(String.class);

        when(permissionManager.hasPermission(any(), permissionCaptor.capture())).thenReturn(true);

        TestUtils.executeCommand("no_permission_annotation", PermissionCommands.class, permissionManager);

        staticMock.verify(PermissionCommands::noPermissionAnnotation, times(1));
        verify(permissionManager, times(1)).hasPermission(any(), any());

        assertEquals("parent", permissionCaptor.getValue());
        assertEquals(1, permissionCaptor.getAllValues().size());

        staticMock.close();
    }

    private static class TestPermissionManager implements PermissionManager<Object> {

        @Override
        public boolean hasPermission(Object source, String permission) {
            return true;
        }

    }

    @RequiresPermission("parent")
    private static class PermissionCommands {

        @RequiresPermission("test")
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
