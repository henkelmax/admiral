package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeConverter;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeSupplier;
import de.maxhenkel.admiral.argumenttype.RangedArgumentTypeSupplier;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class AdmiralArgumentType<S, C, A, T> {

    private AdmiralParameter<S, C, A, T> admiralParameter;
    private Class<T> parameterClass;
    private ArgumentTypeSupplier<S, C, A> argumentType;
    private Class<A> argumentTypeClass;
    @Nullable
    private ArgumentTypeConverter<S, A, T> converter;

    private AdmiralArgumentType() {

    }

    public static <S, C, A, T> AdmiralArgumentType<S, C, A, T> fromClass(AdmiralParameter<S, C, A, T> admiralParameter, Class<T> parameterClass) {
        ArgumentTypeRegistryImpl registry = admiralParameter.getAdmiral().getArgumentRegistry();
        ArgumentTypeSupplier<S, C, A> argumentTypeSupplier = registry.getType(parameterClass);
        if (argumentTypeSupplier == null) {
            throw new IllegalStateException(String.format("ArgumentType %s not registered", parameterClass.getSimpleName()));
        }
        AdmiralArgumentType<S, C, A, T> argumentType = new AdmiralArgumentType<>();
        argumentType.admiralParameter = admiralParameter;
        argumentType.parameterClass = parameterClass;
        argumentType.argumentType = argumentTypeSupplier;
        argumentType.argumentTypeClass = getArgumentTypeClass(argumentType.argumentType.get(admiralParameter.getAdmiral().getCommandBuildContext()));
        argumentType.converter = registry.getConverter(parameterClass);
        return argumentType;
    }

    public Class<T> getParameterClass() {
        return parameterClass;
    }

    public Class<A> getArgumentTypeClass() {
        return argumentTypeClass;
    }

    public AdmiralImpl<S, C> getAdmiral() {
        return admiralParameter.getAdmiral();
    }

    public ArgumentType<A> getArgumentType(@Nullable A min, @Nullable A max) {
        if (argumentType instanceof RangedArgumentTypeSupplier) {
            return ((RangedArgumentTypeSupplier<S, C, A>) argumentType).getRanged(getAdmiral().getCommandBuildContext(), min, max);
        }
        return argumentType.get(getAdmiral().getCommandBuildContext());
    }

    public SuggestionProvider<S> getSuggestionProvider() {
        return argumentType.getSuggestionProvider();
    }

    @Nullable
    public Object convert(CommandContext<S> context, @Nullable A argumentTypeValue) throws CommandSyntaxException {
        if (argumentTypeValue == null && parameterClass.isPrimitive()) {
            return defaultValue(parameterClass);
        }
        if (converter != null) {
            return converter.convertRaw(context, admiralParameter.getName(), argumentTypeValue);
        }
        return argumentTypeValue;
    }

    public static Object defaultValue(Class<?> type) {
        if (type == byte.class) return (byte) 0;
        if (type == short.class) return (short) 0;
        if (type == int.class) return 0;
        if (type == long.class) return 0L;
        if (type == float.class) return 0.0F;
        if (type == double.class) return 0.0D;
        if (type == char.class) return '\u0000';
        if (type == boolean.class) return false;
        throw new IllegalStateException(String.format("Could not find default value for %s", type.getSimpleName()));
    }

    public static <T extends ArgumentType<A>, A> Class<A> getArgumentTypeClass(T argumentType) {
        try {
            Method parse = argumentType.getClass().getDeclaredMethod("parse", StringReader.class);
            parse.setAccessible(true);
            return (Class<A>) parse.getReturnType();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
