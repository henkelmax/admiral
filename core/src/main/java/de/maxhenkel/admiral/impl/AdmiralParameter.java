package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.annotations.Name;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import de.maxhenkel.admiral.impl.arguments.ArgumentBase;

import java.lang.reflect.Parameter;
import java.util.Optional;

public class AdmiralParameter<S, A, T> {

    private final AdmiralMethod<S> admiralMethod;
    private final Parameter parameter;
    private final String name;
    private final Class<T> type;
    private final ArgumentBase<A, T> argumentType;
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
        argumentType = admiralMethod.getAdmiralClass().getArgumentRegistry().get(type);
        if (argumentType == null) {
            throw new IllegalStateException(String.format("ArgumentType %s not registered", type.getSimpleName()));
        }
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

    public AdmiralMethod<S> getAdmiralMethod() {
        return admiralMethod;
    }

    public String getName() {
        return name;
    }

    public boolean isOptional() {
        return optional;
    }

    public RequiredArgumentBuilder<S, A> toArgument() {
        return RequiredArgumentBuilder.argument(name, argumentType.getArgumentType(this).get());
    }

    public Class<T> getRawType() {
        return type;
    }

    public Class<A> getArgumentType() {
        return argumentType.getArgumentTypeClass();
    }

    public Object getValue(CommandContext<S> context) {
        Object value = null;
        try {
            value = context.getArgument(name, argumentType.getArgumentTypeClass());
        } catch (IllegalArgumentException e) {
            if (!optional) {
                throw e;
            }
        }
        if (isJavaOptional(parameter)) {
            return Optional.ofNullable(value);
        }
        return argumentType.convert(context, (A) value);
    }

    public Parameter getParameter() {
        return parameter;
    }
}
