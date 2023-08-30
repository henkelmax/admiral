package de.maxhenkel.admiral.permissions;

public interface PermissionManager<S> {

    boolean hasPermission(S source, String permission);

}
