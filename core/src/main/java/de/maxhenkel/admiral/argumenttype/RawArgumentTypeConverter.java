package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import javax.annotation.Nullable;

@FunctionalInterface
public interface RawArgumentTypeConverter<S, A, T> extends ArgumentTypeConverter<S, A, T> {

    @Nullable
    @Override
    default T convert(CommandContext<S> context, A value) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    T convertRaw(CommandContext<S> context, String name, @Nullable A value) throws CommandSyntaxException;
}
