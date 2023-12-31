package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import javax.annotation.Nullable;
import java.util.Optional;

@FunctionalInterface
public interface OptionalArgumentTypeConverter<S, A, T> extends ArgumentTypeConverter<S, A, T> {

    /**
     * Converts the optional argument type to the desired type.
     *
     * @param context the command context
     * @param value   the optional value
     * @return the converted argument type or <code>null</code> if the object supplied to the command method should be <code>null</code>
     * @throws CommandSyntaxException if the conversion fails
     */
    @Nullable
    T convert(CommandContext<S> context, Optional<A> value) throws CommandSyntaxException;

    @Nullable
    @Override
    default T convert(CommandContext<S> context, A value) throws CommandSyntaxException {
        return convertRaw(context, value);
    }

    @Nullable
    @Override
    default T convertRaw(CommandContext<S> context, @Nullable A value) throws CommandSyntaxException {
        return convert(context, Optional.ofNullable(value));
    }

}
