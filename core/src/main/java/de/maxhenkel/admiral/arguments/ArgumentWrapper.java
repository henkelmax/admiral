package de.maxhenkel.admiral.arguments;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import javax.annotation.Nullable;

/**
 * @param <S> the command source type
 * @param <A> the argument type of the brigadier argument
 * @param <T> the converted argument type
 */
public abstract class ArgumentWrapper<S, A, T> {

    @Nullable
    protected final CommandContext<S> context;
    protected final A value;

    /**
     * This constructor gets invoked automatically.
     * Don't call it manually or add any parameters.
     * <p>
     * Alternatively, you can define a constructor that only takes {@link #value}.
     *
     * @param context The command context
     * @param value   The argument value
     */
    public ArgumentWrapper(@Nullable CommandContext<S> context, A value) {
        this.context = context;
        this.value = value;
    }

    /**
     * @return The argument value
     */
    public T get() throws CommandSyntaxException {
        return convert(value);
    }

    /**
     * Converts the argument value to the desired type.
     *
     * @param value the argument value
     * @return the converted argument value
     * @throws CommandSyntaxException if the argument value is invalid
     */
    protected abstract T convert(A value) throws CommandSyntaxException;

}
