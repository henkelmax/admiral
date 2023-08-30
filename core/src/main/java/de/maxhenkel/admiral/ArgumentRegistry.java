package de.maxhenkel.admiral;

import com.mojang.brigadier.arguments.ArgumentType;

import java.util.function.Supplier;

public interface ArgumentRegistry {

    <A, T> void register(Supplier<ArgumentType<A>> argumentType, Class<A> argumentTypeClass, Class<T>... wrapperClasses);

    <A> void register(Supplier<ArgumentType<A>> argumentType, Class<A> argumentTypeClass);

}
