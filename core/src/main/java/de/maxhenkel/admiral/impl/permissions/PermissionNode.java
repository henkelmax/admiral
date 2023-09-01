package de.maxhenkel.admiral.impl.permissions;

import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;

public class PermissionNode<S> implements Permission<S> {

    protected String permission;

    public PermissionNode(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }


    @Override
    public boolean hasPermission(S source, @Nullable PermissionManager<S> permissionManager) {
        if (permissionManager == null) {
            return true;
        }
        return permissionManager.hasPermission(source, permission);
    }
}
