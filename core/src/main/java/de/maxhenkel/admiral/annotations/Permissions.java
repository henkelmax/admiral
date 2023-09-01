package de.maxhenkel.admiral.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Permissions {

    RequiresPermission[] value() default {};

}
