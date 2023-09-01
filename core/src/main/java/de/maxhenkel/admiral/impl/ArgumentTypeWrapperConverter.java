package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeConverter;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeSupplier;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeWrapper;
import de.maxhenkel.admiral.argumenttype.RangedArgumentTypeSupplier;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ArgumentTypeWrapperConverter {

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

    public static <S, A, W extends ArgumentTypeWrapper<S, A, ?>> void register(ArgumentTypeRegistryImpl registry, Class<W> argumentTypeWrapperClass) {
        try {
            registerInternal(registry, argumentTypeWrapperClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <S, A, W extends ArgumentTypeWrapper<S, A, ?>> void registerInternal(ArgumentTypeRegistryImpl registry, Class<W> argumentTypeWrapperClass) throws Exception {
        Constructor<W> constructor = argumentTypeWrapperClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        W w = constructor.newInstance();

        ArgumentTypeSupplier<A> argumentTypeSupplier = (RangedArgumentTypeSupplier<A>) (min, max) -> {
            try {
                return (ArgumentType<A>) getArgumentType.invoke(w, min, max);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        ArgumentTypeConverter<S, A, W> converter = (CommandContext<S> context, @Nullable A argument) -> {
            try {
                W wrapper = constructor.newInstance();
                Object converted = convert.invoke(wrapper, context, argument);
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
        };

        registry.register(argumentTypeSupplier, converter, argumentTypeWrapperClass);
    }

}
