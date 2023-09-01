package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.*;

import javax.annotation.Nullable;
import java.lang.reflect.Parameter;
import java.util.Optional;

public class AdmiralParameter<S, A, T> {

    private final AdmiralMethod<S> admiralMethod;
    private final Parameter parameter;
    private final String name;
    /**
     * The type of the parameter.
     * If the parameter is an optional, the type of the optional is used.
     */
    private final Class<T> type;
    private final AdmiralArgumentType<S, A, T> argumentType;
    private final boolean optional;

    public AdmiralParameter(AdmiralMethod<S> admiralMethod, Parameter parameter) {
        this.admiralMethod = admiralMethod;
        this.parameter = parameter;
        name = getArgumentName(parameter);
        if (isJavaOptional(parameter)) {
            type = (Class<T>) ReflectionUtil.classFromType(parameter.getParameterizedType());
        } else {
            type = (Class<T>) parameter.getType();
        }
        argumentType = AdmiralArgumentType.fromClass(getAdmiral().getArgumentRegistry(), type);
        optional = isOptional(parameter);
    }

    private static String getArgumentName(Parameter parameter) {
        Name nameAnnotation = parameter.getDeclaredAnnotation(Name.class);
        return nameAnnotation != null ? nameAnnotation.value() : parameter.getName();
    }

    private static boolean isOptional(Parameter parameter) {
        if (isJavaOptional(parameter)) {
            return true;
        }
        return parameter.getDeclaredAnnotation(OptionalArgument.class) != null;
    }

    private static boolean isJavaOptional(Parameter parameter) {
        return Optional.class.isAssignableFrom(parameter.getType());
    }

    public AdmiralArgumentType<S, A, T> getArgumentType() {
        return argumentType;
    }

    public AdmiralMethod<S> getAdmiralMethod() {
        return admiralMethod;
    }

    public AdmiralClass<S> getAdmiralClass() {
        return admiralMethod.getAdmiralClass();
    }

    public AdmiralImpl<S> getAdmiral() {
        return admiralMethod.getAdmiralClass().getAdmiral();
    }

    public String getName() {
        return name;
    }

    public boolean isOptional() {
        return optional;
    }

    public RequiredArgumentBuilder<S, A> toArgument() {
        return RequiredArgumentBuilder.argument(name, argumentType.getArgumentType(getMin(), getMax()));
    }

    public Object getValue(CommandContext<S> context) throws CommandSyntaxException {
        A value = null;
        try {
            value = (A) context.getArgument(name, Object.class);
        } catch (IllegalArgumentException e) {
            if (!optional) {
                throw e;
            }
        }
        Object convertedValue = argumentType.convert(context, value);
        if (isJavaOptional(parameter)) {
            return Optional.ofNullable(convertedValue);
        }
        return convertedValue;
    }

    @Nullable
    private A getMin() {
        String value = null;
        MinMax minMax = parameter.getDeclaredAnnotation(MinMax.class);
        Min min = parameter.getDeclaredAnnotation(Min.class);
        if (minMax != null) {
            value = minMax.min();
        } else if (min != null) {
            value = min.value();
        }
        if (value == null) {
            return null;
        }
        return parseString(value, argumentType.getArgumentTypeClass());
    }

    @Nullable
    private A getMax() {
        String value = null;
        MinMax minMax = parameter.getDeclaredAnnotation(MinMax.class);
        Max max = parameter.getDeclaredAnnotation(Max.class);
        if (minMax != null) {
            value = minMax.max();
        } else if (max != null) {
            value = max.value();
        }
        if (value == null) {
            return null;
        }
        return parseString(value, argumentType.getArgumentTypeClass());
    }

    private static <A> A parseString(String value, Class<A> clazz) {
        if (Byte.class.isAssignableFrom(clazz)) {
            return (A) (Byte) Byte.parseByte(value);
        } else if (byte.class.isAssignableFrom(clazz)) {
            return (A) (Byte) Byte.parseByte(value);
        } else if (Short.class.isAssignableFrom(clazz)) {
            return (A) (Short) Short.parseShort(value);
        } else if (short.class.isAssignableFrom(clazz)) {
            return (A) (Short) Short.parseShort(value);
        } else if (Integer.class.isAssignableFrom(clazz)) {
            return (A) (Integer) Integer.parseInt(value);
        } else if (int.class.isAssignableFrom(clazz)) {
            return (A) (Integer) Integer.parseInt(value);
        } else if (Long.class.isAssignableFrom(clazz)) {
            return (A) (Long) Long.parseLong(value);
        } else if (long.class.isAssignableFrom(clazz)) {
            return (A) (Long) Long.parseLong(value);
        } else if (Float.class.isAssignableFrom(clazz)) {
            return (A) (Float) Float.parseFloat(value);
        } else if (float.class.isAssignableFrom(clazz)) {
            return (A) (Float) Float.parseFloat(value);
        } else if (Double.class.isAssignableFrom(clazz)) {
            return (A) (Double) Double.parseDouble(value);
        } else if (double.class.isAssignableFrom(clazz)) {
            return (A) (Double) Double.parseDouble(value);
        }
        return null;
    }
}
