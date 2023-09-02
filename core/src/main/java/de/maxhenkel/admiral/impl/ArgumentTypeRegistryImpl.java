package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.arguments.*;
import de.maxhenkel.admiral.arguments.GreedyString;
import de.maxhenkel.admiral.arguments.Word;
import de.maxhenkel.admiral.argumenttype.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ArgumentTypeRegistryImpl implements ArgumentTypeRegistry {

    private static final List<Consumer<ArgumentTypeRegistryImpl>> DEFAULT_REGISTRATIONS = new ArrayList<>();

    private final Map<Class<?>, ArgumentTypeSupplier<?, ?, ?>> argumentTypeMap;
    private final Map<Class<?>, ArgumentTypeConverter<?, ?, ?>> argumentTypeConverterMap;

    public ArgumentTypeRegistryImpl() {
        argumentTypeMap = new HashMap<>();
        argumentTypeConverterMap = new HashMap<>();
        registerDefault();
        for (Consumer<ArgumentTypeRegistryImpl> consumer : DEFAULT_REGISTRATIONS) {
            consumer.accept(this);
        }
    }

    public static void addRegistrations(Consumer<ArgumentTypeRegistryImpl> argumentRegistryConsumer) {
        DEFAULT_REGISTRATIONS.add(argumentRegistryConsumer);
    }

    private void registerDefault() {
        register(String.class, StringArgumentType::string);
        register(GreedyString.class, StringArgumentType::greedyString, (context, value) -> new GreedyString(value));
        register(Word.class, StringArgumentType::word, (context, value) -> new Word(value));

        register((RangedArgumentTypeSupplier<?, ?, Long>) (min, max) -> {
            if (min == null) {
                min = Long.MIN_VALUE;
            }
            if (max == null) {
                max = Long.MAX_VALUE;
            }
            return LongArgumentType.longArg(min, max);
        }, Long.class, long.class);
        register((RangedArgumentTypeSupplier<?, ?, Integer>) (min, max) -> {
            if (min == null) {
                min = Integer.MIN_VALUE;
            }
            if (max == null) {
                max = Integer.MAX_VALUE;
            }
            return IntegerArgumentType.integer(min, max);
        }, Integer.class, int.class);
        register((RangedArgumentTypeSupplier<?, ?, Double>) (min, max) -> {
            if (min == null) {
                min = Double.MIN_VALUE;
            }
            if (max == null) {
                max = Double.MAX_VALUE;
            }
            return DoubleArgumentType.doubleArg(min, max);
        }, Double.class, double.class);
        register((RangedArgumentTypeSupplier<?, ?, Float>) (min, max) -> {
            if (min == null) {
                min = Float.MIN_VALUE;
            }
            if (max == null) {
                max = Float.MAX_VALUE;
            }
            return FloatArgumentType.floatArg(min, max);
        }, Float.class, float.class);

        register(BoolArgumentType::bool, Boolean.class, boolean.class);
    }

    @Nullable
    public <S, C, A, T> ArgumentTypeSupplier<S, C, A> getType(Class<T> clazz) {
        return (ArgumentTypeSupplier<S, C, A>) argumentTypeMap.get(clazz);
    }

    @Nullable
    public <S, A, T> ArgumentTypeConverter<S, A, T> getConverter(Class<T> clazz) {
        return (ArgumentTypeConverter<S, A, T>) argumentTypeConverterMap.get(clazz);
    }

    @Override
    public <S, C, A, T> void register(Class<T> customTypeClass, ArgumentTypeSupplier<S, C, A> argumentType, @Nullable ArgumentTypeConverter<S, A, T> argumentTypeConverter) {
        argumentTypeMap.put(customTypeClass, argumentType);
        if (argumentTypeConverter != null) {
            argumentTypeConverterMap.put(customTypeClass, argumentTypeConverter);
        }
    }

    @Override
    public <S, C, A> void register(Class<A> argumentTypeClass, ArgumentTypeSupplier<S, C, A> argumentType) {
        register(argumentTypeClass, argumentType, null);
    }

    public <S, C, A> void register(ArgumentTypeSupplier<S, C, A> argumentType, Class<A>... argumentTypeClass) {
        for (Class<A> clazz : argumentTypeClass) {
            register(clazz, argumentType, null);
        }
    }

}
