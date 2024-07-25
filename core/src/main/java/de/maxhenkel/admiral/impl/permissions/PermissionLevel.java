package de.maxhenkel.admiral.impl.permissions;

import de.maxhenkel.admiral.impl.PermissionLevelManager;
import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;

public class PermissionLevel<S> implements Permission<S> {

    protected int permissionLevel;

    public PermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    @Override
    public boolean hasPermission(S source, @Nullable PermissionManager<S> permissionManager) {
        PermissionLevelManager.PermissionLevelAccessor<Object> permissionLevelAccessor = PermissionLevelManager.getPermissionLevelAccessor();
        if (permissionLevelAccessor == null) {
            throw new IllegalStateException("Command source does not have a permission level");
        }
        return permissionLevelAccessor.hasPermissionLevel(source, permissionLevel);
    }
}
