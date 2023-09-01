package de.maxhenkel.admiral.annotations;

import de.maxhenkel.admiral.permissions.PermissionManager;

import java.lang.annotation.*;

/**
 * Sets the required permission level for a command.
 * This isn't handled by the {@link PermissionManager}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface RequiresPermissionLevel {

    /**
     * @return the required permission level
     */
    int value();

}
