package de.maxhenkel.admiral.impl.permissions;

import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;

public interface Permission<S> {

    boolean hasPermission(S source, @Nullable PermissionManager<S> permissionManager);

    static <S> Permission<S> level(int level) {
        return new PermissionLevel<>(level);
    }

}
