package de.maxhenkel.admiral.permissions;

import de.maxhenkel.admiral.annotations.RequiresPermission;
import de.maxhenkel.admiral.Admiral;

/**
 * A permission manager that handles permissions defined with {@link RequiresPermission}.
 * <br/>
 * Must be registered with {@link Admiral.Builder#setPermissionManager(PermissionManager)}.
 *
 * @param <S> the command source type
 */
public interface PermissionManager<S> {

    /**
     * Checks if the source has the permission.
     *
     * @param source     the command source
     * @param permission the permission node
     * @return true if the source has the permission
     */
    boolean hasPermission(S source, String permission);

}
