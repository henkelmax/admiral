package de.maxhenkel.admiral;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.permissions.PermissionManager;

public class TestUtils {

    public static void executeCommand(String command, Class<?> commandClass) throws CommandSyntaxException {
        executeCommand(command, commandClass, null);
    }

    public static void executeCommand(String command, Class<?> commandClass, PermissionManager<Object> permissionManager) throws CommandSyntaxException {
        CommandDispatcher<Object> dispatcher = new CommandDispatcher<>();
        MinecraftAdmiral.builder(dispatcher).addCommandClasses(commandClass).setPermissionManager(permissionManager).build();
        dispatcher.execute(command, new Object());
    }

}
