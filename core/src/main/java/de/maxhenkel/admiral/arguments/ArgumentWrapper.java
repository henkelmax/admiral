package de.maxhenkel.admiral.arguments;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import javax.annotation.Nullable;

public abstract class ArgumentWrapper<S, A, T> {

    @Nullable
    protected final CommandContext<S> context;
    protected final A value;

    public ArgumentWrapper(@Nullable CommandContext<S> context, A value) {
        this.context = context;
        this.value = value;
    }

    public T get() throws CommandSyntaxException {
        return convert(value);
    }

    protected abstract T convert(A value) throws CommandSyntaxException;

}
