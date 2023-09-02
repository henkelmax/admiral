package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.arguments.ArgumentType;

import javax.annotation.Nullable;

public interface ContextArgumentTypeSupplier<S, C, A> extends ArgumentTypeSupplier<S, C, A> {

    @Override
    default ArgumentType<A> get() {
        throw new UnsupportedOperationException();
    }

    @Override
    ArgumentType<A> get(@Nullable C commandBuildContext);

}
