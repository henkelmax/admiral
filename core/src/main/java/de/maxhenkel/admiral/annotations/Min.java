package de.maxhenkel.admiral.annotations;

import java.lang.annotation.*;

/**
 * An annotation to set a minimum value for a command argument.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface Min {

    /**
     * The string value will be automatically converted to the correct type.
     * <br/>
     * Examples:
     * <pre>{@code
     * void test(@Min("10") int value) {
     *    // ...
     * }
     * }</pre>
     *
     * <pre>{@code
     * void test(@Min("10.0") double value) {
     *    // ...
     * }
     * }</pre>
     *
     * @return the minimum value
     */
    String value();

}
