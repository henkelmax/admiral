package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

@FunctionalInterface
public interface CommandBiFunction<T, U, R> {

    R apply(T t, U u) throws CommandSyntaxException;

}
