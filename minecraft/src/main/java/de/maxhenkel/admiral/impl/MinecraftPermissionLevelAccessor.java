package de.maxhenkel.admiral.impl;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;

public class MinecraftPermissionLevelAccessor<S> implements PermissionLevelManager.PermissionLevelAccessor<S> {

    @Override
    public boolean hasPermissionLevel(S source, int level) {
        if (source instanceof CommandSourceStack) {
            return ((CommandSourceStack) source).permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.byId(level)));
        }
        return false;
    }

}
