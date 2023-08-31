package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.context.CommandContext;

/**
 * A wrapper for a simple argument.
 *
 * @param <A> the argument type of the brigadier argument
 */
public abstract class SimpleArgumentTypeWrapper<A> extends ArgumentTypeWrapper<Object, A, A> {

    @Override
    protected A convert(CommandContext<Object> context, A value) {
        return value;
    }

}
