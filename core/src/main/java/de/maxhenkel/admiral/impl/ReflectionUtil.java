package de.maxhenkel.admiral.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtil {

    public static Class<?> classFromType(Type type) {
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalStateException("Type must be an instance of ParameterizedType");
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

}
