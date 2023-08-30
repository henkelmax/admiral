package de.maxhenkel.admiral.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Repeatable(Commands.class)
public @interface Command {

    String[] value() default {};

}
