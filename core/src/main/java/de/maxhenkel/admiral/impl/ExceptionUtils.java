package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class ExceptionUtils {

    public static RuntimeException getAsCommandSyntaxException(Throwable e) throws CommandSyntaxException {
        return getAs(e, CommandSyntaxException.class);
    }

    public static <E extends Throwable> RuntimeException getAs(Throwable e, Class<E> exceptionClass) throws E {
        if (exceptionClass.isAssignableFrom(e.getClass())) {
            throw (E) e;
        } else if (exceptionClass.isAssignableFrom(e.getCause().getClass())) {
            return getAs(e.getCause(), exceptionClass);
        } else {
            if (e instanceof RuntimeException) {
                return (RuntimeException) e;
            } else {
                return new RuntimeException(e);
            }
        }
    }

}
