package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Min;
import de.maxhenkel.admiral.annotations.Max;
import de.maxhenkel.admiral.annotations.MinMax;

import javax.annotation.Nullable;

/**
 * A wrapper for brigadier argument types.
 *
 * @param <S> the command source type
 * @param <A> the argument type of the brigadier argument
 * @param <T> the converted argument type
 */
public abstract class ArgumentTypeWrapper<S, A, T> {

    /**
     * This value is set via reflection.
     */
    private T value;

    /**
     * Wrappers always need a no-argument constructor.
     */
    protected ArgumentTypeWrapper() {

    }

    /**
     * @return The argument value
     */
    public T get() {
        return value;
    }

    /**
     * Converts the argument value to the desired type.
     *
     * @param context the command context
     * @param value   the argument value or <code>null</code> if the argument is optional and not present
     * @return the converted argument value
     * @throws CommandSyntaxException if the argument value is invalid
     */
    protected abstract T convert(CommandContext<S> context, @Nullable A value) throws CommandSyntaxException;

    /**
     * @return the argument type
     */
    protected abstract ArgumentType<A> getArgumentType();

    /**
     * The minimum and maximum values are provided by the {@link Min}, {@link Max} and {@link MinMax} annotations.
     *
     * @param min the minimum value or <code>null</code> if there is no minimum
     * @param max the maximum value or <code>null</code> if there is no maximum
     * @return the argument type
     */
    protected ArgumentType<A> getArgumentType(@Nullable A min, @Nullable A max) {
        return getArgumentType();
    }

    @Override
    public String toString() {
        if (value == null) {
            return super.toString();
        }
        return value.toString();
    }
}
