package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Name;
import de.maxhenkel.admiral.annotations.OptionalArgument;

import java.lang.reflect.Parameter;
import java.util.Optional;

public class AdmiralParameter<S, A, T> {

    private final AdmiralMethod<S> admiralMethod;
    private final Parameter parameter;
    private final String name;
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
        try {
            argumentType = AdmiralArgumentType.fromClass(getAdmiral().getArgumentRegistry(), type);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        A minVal = AnnotationUtil.getMin(this);
        A maxVal = AnnotationUtil.getMax(this);
        return RequiredArgumentBuilder.argument(name, argumentType.getArgumentType(minVal, maxVal));
    }

    public Class<T> getParameterType() {
        return type;
    }

    public Class<A> getArgumentType() {
        return argumentType.getArgumentTypeClass();
    }

    public Object getValue(CommandContext<S> context) throws CommandSyntaxException {
        A value = null;
        try {
            value = context.getArgument(name, argumentType.getArgumentTypeClass());
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

    public Parameter getParameter() {
        return parameter;
    }
}
