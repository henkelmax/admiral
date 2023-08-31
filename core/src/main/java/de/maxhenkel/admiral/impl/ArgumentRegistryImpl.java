package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import de.maxhenkel.admiral.arguments.ArgumentRegistry;
import de.maxhenkel.admiral.arguments.GreedyString;
import de.maxhenkel.admiral.arguments.Word;
import de.maxhenkel.admiral.impl.arguments.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArgumentRegistryImpl implements ArgumentRegistry {

    private static final List<Consumer<ArgumentRegistryImpl>> DEFAULT_REGISTRATIONS = new ArrayList<>();

    private final Map<Class<?>, ArgumentBase<?, ?>> argumentTypeMap;

    public ArgumentRegistryImpl() {
        argumentTypeMap = new HashMap<>();
        registerDefault();
        for (Consumer<ArgumentRegistryImpl> consumer : DEFAULT_REGISTRATIONS) {
            consumer.accept(this);
        }
    }

    public static void addRegistrations(Consumer<ArgumentRegistryImpl> argumentRegistryConsumer) {
        DEFAULT_REGISTRATIONS.add(argumentRegistryConsumer);
    }

    private void registerDefault() {
        register(StringArgumentType::string, String.class, String.class);
        register(StringArgumentType::word, String.class, Word.class);
        register(StringArgumentType::greedyString, String.class, GreedyString.class);

        registerBounded(Integer.class, Integer.class, IntegerArgumentType::integer, IntegerArgumentType::integer, max -> IntegerArgumentType.integer(Integer.MIN_VALUE, max), IntegerArgumentType::integer);
        registerBounded(int.class, int.class, IntegerArgumentType::integer, IntegerArgumentType::integer, max -> IntegerArgumentType.integer(Integer.MIN_VALUE, max), IntegerArgumentType::integer);
    }

    @Nullable
    public <A, T> ArgumentBase<A, T> get(Class<T> clazz) {
        return (ArgumentBase<A, T>) argumentTypeMap.get(clazz);
    }

    @Override
    public <A, T> void register(Supplier<ArgumentType<A>> argumentType, Class<A> argumentTypeClass, Class<T>... wrapperClasses) {
        for (Class<T> wrapperClass : wrapperClasses) {
            argumentTypeMap.put(wrapperClass, new ArgumentWrapper<>(argumentTypeClass, wrapperClass, argumentType));
        }
    }

    @Override
    public <A> void register(Supplier<ArgumentType<A>> argumentType, Class<A> argumentTypeClass) {
        argumentTypeMap.put(argumentTypeClass, new ArgumentWrapper<>(argumentTypeClass, argumentTypeClass, argumentType));
    }

    public <A, T> void registerBounded(Class<A> argumentTypeClass, Class<T> clazz, MinMaxArgumentTypeSupplier<A> minMax, MinArgumentTypeSupplier<A> min, MaxArgumentTypeSupplier<A> max, ArgumentTypeSupplier<A> arg) {
        argumentTypeMap.put(clazz, new MinMaxArgumentWrapper<>(argumentTypeClass, clazz, minMax, min, max, arg));
    }

}
