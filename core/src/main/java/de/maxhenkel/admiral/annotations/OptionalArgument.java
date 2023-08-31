package de.maxhenkel.admiral.annotations;

import javax.annotation.Nullable;
import java.lang.annotation.*;

/**
 * Marks a command argument as optional.
 * Alternatively use {@link java.util.Optional} to mark a command argument as optional.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Nullable
@Documented
public @interface OptionalArgument {
}
