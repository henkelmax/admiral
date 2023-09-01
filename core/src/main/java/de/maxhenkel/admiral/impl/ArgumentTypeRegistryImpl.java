package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
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

    private final Map<Class<?>, ArgumentTypeSupplier<?>> argumentTypeMap;
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
        register(StringArgumentType::string, String.class);
        register(StringArgumentType::greedyString, (context, value) -> new GreedyString(value), GreedyString.class);
        register(StringArgumentType::word, (context, value) -> new Word(value), Word.class);

        register((RangedArgumentTypeSupplier<Integer>) (min, max) -> {
            if (min == null) {
                min = Integer.MIN_VALUE;
            }
            if (max == null) {
                max = Integer.MAX_VALUE;
            }
            return IntegerArgumentType.integer(min, max);
        }, Integer.class, int.class);
    }

    @Nullable
    public <A, T> ArgumentTypeSupplier<A> getType(Class<T> clazz) {
        return (ArgumentTypeSupplier<A>) argumentTypeMap.get(clazz);
    }

    @Nullable
    public <S, A, T> ArgumentTypeConverter<S, A, T> getConverter(Class<T> clazz) {
        return (ArgumentTypeConverter<S, A, T>) argumentTypeConverterMap.get(clazz);
    }

    @Override
    public <S, A, T> void register(ArgumentTypeSupplier<A> argumentType, @Nullable ArgumentTypeConverter<S, A, T> argumentTypeConverter, Class<T> customTypeClass) {
        argumentTypeMap.put(customTypeClass, argumentType);
        if (argumentTypeConverter != null) {
            argumentTypeConverterMap.put(customTypeClass, argumentTypeConverter);
        }
    }

    @Override
    public <A> void register(ArgumentTypeSupplier<A> argumentType, Class<A> argumentTypeClass) {
        register(argumentType, null, argumentTypeClass);
    }

    @Override
    public <S, A, T> void register(Class<? extends ArgumentTypeWrapper<S, A, T>> argumentTypeWrapperClass) {
        ArgumentTypeWrapperConverter.register(this, argumentTypeWrapperClass);
    }

    public <A> void register(ArgumentTypeSupplier<A> argumentType, Class<A>... argumentTypeClass) {
        for (Class<A> clazz : argumentTypeClass) {
            register(argumentType, null, clazz);
        }
    }

}
