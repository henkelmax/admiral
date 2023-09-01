package de.maxhenkel.admiral.impl.permissions;

import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

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
        try {
            Method hasPermission = source.getClass().getDeclaredMethod("hasPermission", int.class);
            Object invoke = hasPermission.invoke(source, permissionLevel);
            if (!(invoke instanceof Boolean)) {
                throw new IllegalStateException("hasPermission method must return boolean");
            }
            return (Boolean) invoke;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
