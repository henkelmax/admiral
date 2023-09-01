package de.maxhenkel.admiral.annotations;

import de.maxhenkel.admiral.permissions.PermissionManager;

import java.lang.annotation.*;

/**
 * Sets the permission node for a command.
 * Permissions must be handled by the {@link PermissionManager}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Repeatable(Permissions.class)
@Documented
public @interface RequiresPermission {

    /**
     * @return the permission node
     */
    String value();

}
