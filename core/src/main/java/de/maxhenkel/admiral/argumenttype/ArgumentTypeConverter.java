package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import javax.annotation.Nullable;

/**
 * Converts an argument type.
 * Use {@link OptionalArgumentTypeConverter} if you want to handle optional arguments yourself.
 *
 * @param <S> the command source type
 * @param <A> the argument type of the brigadier argument
 * @param <T> the converted argument type
 */
@FunctionalInterface
public interface ArgumentTypeConverter<S, A, T> {

    @Nullable
    default T convertRaw(CommandContext<S> context, @Nullable A value) throws CommandSyntaxException {
        if (value == null) {
            return null;
        }
        return convert(context, value);
    }

    @Nullable
    T convert(CommandContext<S> context, A value) throws CommandSyntaxException;

}
