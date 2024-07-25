package de.maxhenkel.admiral.impl;

import net.minecraft.commands.CommandSourceStack;

public class MinecraftPermissionLevelAccessor<S> implements PermissionLevelManager.PermissionLevelAccessor<S> {

    @Override
    public boolean hasPermissionLevel(S source, int level) {
        if (source instanceof CommandSourceStack) {
            return ((CommandSourceStack) source).hasPermission(level);
        }
        return false;
    }

}
