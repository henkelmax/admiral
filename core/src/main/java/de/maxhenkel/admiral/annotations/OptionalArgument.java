package de.maxhenkel.admiral.annotations;

import java.lang.annotation.*;

/**
 * Marks a command argument as optional.
 * Alternatively use {@link java.util.Optional} to mark a command argument as optional.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface OptionalArgument {
}
