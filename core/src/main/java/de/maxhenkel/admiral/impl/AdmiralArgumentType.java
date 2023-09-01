package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeWrapper;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeConverter;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeSupplier;
import de.maxhenkel.admiral.argumenttype.RangedArgumentTypeSupplier;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AdmiralArgumentType<S, A, T> {

    public static final Method getArgumentType;
    public static final Method convert;
    public static final Field value;

    static {
        try {
            getArgumentType = ArgumentTypeWrapper.class.getDeclaredMethod("getArgumentType", Object.class, Object.class);
            getArgumentType.setAccessible(true);
            convert = ArgumentTypeWrapper.class.getDeclaredMethod("convert", CommandContext.class, Object.class);
            convert.setAccessible(true);
            value = ArgumentTypeWrapper.class.getDeclaredField("value");
            value.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class<T> parameterClass;
    private ArgumentTypeSupplier<A> argumentType;
    private Class<A> argumentTypeClass;
    @Nullable
    private Constructor<T> wrapperConstructor;
    @Nullable
    private ArgumentTypeConverter<S, A, T> converter;

    private AdmiralArgumentType() {

    }

    public static <S, A, T> AdmiralArgumentType<S, A, T> fromClass(ArgumentTypeRegistryImpl registry, Class<T> parameterClass) throws Exception {
        if (ArgumentTypeWrapper.class.isAssignableFrom(parameterClass)) {
            return fromWrapper(parameterClass);
        }
        ArgumentTypeSupplier<A> argumentTypeSupplier = registry.getType(parameterClass);
        if (argumentTypeSupplier == null) {
            throw new IllegalStateException(String.format("ArgumentType %s not registered", parameterClass.getSimpleName()));
        }
        AdmiralArgumentType<S, A, T> argumentType = new AdmiralArgumentType<>();
        argumentType.parameterClass = parameterClass;
        argumentType.argumentType = argumentTypeSupplier;
        argumentType.argumentTypeClass = getArgumentTypeClass(argumentType.argumentType.get());
        argumentType.converter = registry.getConverter(parameterClass);
        return argumentType;
    }

    private static <S, A, T> AdmiralArgumentType<S, A, T> fromWrapper(Class<T> clazz) throws Exception {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        ArgumentTypeWrapper<S, A, T> wrapper = (ArgumentTypeWrapper<S, A, T>) constructor.newInstance();

        AdmiralArgumentType<S, A, T> argumentType = new AdmiralArgumentType<>();

        argumentType.parameterClass = clazz;
        argumentType.argumentType = (RangedArgumentTypeSupplier<A>) (min, max) -> {
            try {
                return (ArgumentType<A>) getArgumentType.invoke(wrapper, min, max);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        argumentType.argumentTypeClass = getArgumentTypeClass(argumentType.argumentType.get());
        argumentType.wrapperConstructor = constructor;

        return argumentType;
    }

    public Class<T> getParameterClass() {
        return parameterClass;
    }

    public Class<A> getArgumentTypeClass() {
        return argumentTypeClass;
    }

    public ArgumentType<A> getArgumentType(@Nullable A min, @Nullable A max) {
        if (argumentType instanceof RangedArgumentTypeSupplier) {
            return ((RangedArgumentTypeSupplier<A>) argumentType).getRanged(min, max);
        }
        return argumentType.get();
    }

    @Nullable
    public Object convert(CommandContext<S> context, @Nullable A argumentTypeValue) throws CommandSyntaxException {
        if (wrapperConstructor == null) {
            if (argumentTypeValue == null && parameterClass.isPrimitive()) {
                return defaultValue(parameterClass);
            }
            if (converter != null) {
                return converter.convertRaw(context, argumentTypeValue);
            }
            return argumentTypeValue;
        }
        try {
            ArgumentTypeWrapper<S, A, T> wrapper = (ArgumentTypeWrapper<S, A, T>) wrapperConstructor.newInstance();
            Object converted = convert.invoke(wrapper, context, argumentTypeValue);
            if (converted == null) {
                return null;
            }
            value.set(wrapper, converted);
            return wrapper;
        } catch (Exception e) {
            if (e instanceof CommandSyntaxException) {
                throw (CommandSyntaxException) e;
            }
            if (e.getCause() instanceof CommandSyntaxException) {
                throw (CommandSyntaxException) e.getCause();
            }
            throw new RuntimeException(e);
        }
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
