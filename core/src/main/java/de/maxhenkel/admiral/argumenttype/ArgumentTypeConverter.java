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

    /**
     * Converts the argument type to the desired type.
     *
     * @param context the command context
     * @param value   the argument type value or <code>null</code> if the argument is optional
     * @return the converted argument type or <code>null</code> if the object supplied to the command method should be <code>null</code>
     * @throws CommandSyntaxException if the conversion fails
     */
    @Nullable
    default T convertRaw(CommandContext<S> context, @Nullable A value) throws CommandSyntaxException {
        if (value == null) {
            return null;
        }
        return convert(context, value);
    }

    /**
     * Converts the argument type to the desired type.
     *
     * @param context the command context
     * @param name    the name of the argument
     * @param value   the argument type value or <code>null</code> if the argument is optional
     * @return the converted argument type or <code>null</code> if the object supplied to the command method should be <code>null</code>
     * @throws CommandSyntaxException if the conversion fails
     */
    @Nullable
    default T convertRaw(CommandContext<S> context, String name, @Nullable A value) throws CommandSyntaxException {
        return convertRaw(context, value);
    }

    /**
     * Converts the argument type to the desired type.
     *
     * @param context the command context
     * @param value   the argument type value
     * @return the converted argument type
     * @throws CommandSyntaxException if the conversion fails
     */
    @Nullable
    T convert(CommandContext<S> context, A value) throws CommandSyntaxException;

}
