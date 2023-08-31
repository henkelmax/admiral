package de.maxhenkel.admiral.annotations;

import java.lang.annotation.*;

/**
 * An annotation for parameters to set the name of a command argument.
 * If this isn't set <code>arg0</code>, <code>arg1</code>, ... will be used.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface Name {

    /**
     * @return the name of the command argument
     */
    String value();

}
