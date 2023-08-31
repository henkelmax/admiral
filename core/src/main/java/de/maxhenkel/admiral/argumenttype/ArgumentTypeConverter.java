package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ArgumentTypeConverter<S, A, T> {

    T convert(CommandContext<S> context, @Nullable A value) throws CommandSyntaxException;

}
