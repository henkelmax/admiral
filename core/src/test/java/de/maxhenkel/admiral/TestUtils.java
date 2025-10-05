package de.maxhenkel.admiral;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.impl.PermissionLevelManager;
import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;

public class TestUtils {

    public static void executeCommand(String command, Class<?> commandClass) throws CommandSyntaxException {
        executeCommand(command, commandClass, Integer.MAX_VALUE, null);
    }

    public static void executeCommand(String command, Class<?> commandClass, int permissionLevel) throws CommandSyntaxException {
        executeCommand(command, commandClass, permissionLevel, null);
    }

    public static void executeCommand(String command, Class<?> commandClass, PermissionManager<Source> permissionManager) throws CommandSyntaxException {
        executeCommand(command, commandClass, Integer.MAX_VALUE, permissionManager);
    }

    public static void executeCommand(String command, Class<?> commandClass, int permissionLevel, @Nullable PermissionManager<Source> permissionManager) throws CommandSyntaxException {
        CommandDispatcher<Source> dispatcher = new CommandDispatcher<>();
        PermissionLevelManager.setPermissionLevelAccessor((source, level) -> ((Source) source).hasPermission(level));
        Admiral.builder(dispatcher).addCommandClasses(commandClass).setPermissionManager(permissionManager).build();
        dispatcher.execute(command, new Source(permissionLevel));
    }

    public static class Source {
        private final int permissionLevel;

        public Source(int permissionLevel) {
            this.permissionLevel = permissionLevel;
        }

        public boolean hasPermission(int level) {
            return permissionLevel >= level;
        }
    }

}
