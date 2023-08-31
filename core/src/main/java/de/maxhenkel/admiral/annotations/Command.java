package de.maxhenkel.admiral.annotations;

import java.lang.annotation.*;

/**
 * Marks a method or class as a command.
 * Use this annotation multiple times to add multiple paths (aliases) to the command.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Repeatable(Commands.class)
@Documented
public @interface Command {

    /**
     * @return the command path
     */
    String[] value() default {};

}
