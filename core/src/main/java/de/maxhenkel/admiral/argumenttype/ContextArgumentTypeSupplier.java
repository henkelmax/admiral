package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.arguments.ArgumentType;

import javax.annotation.Nullable;

public interface ContextArgumentTypeSupplier<C, A> extends ArgumentTypeSupplier<C, A> {

    @Override
    default ArgumentType<A> get() {
        throw new UnsupportedOperationException();
    }

    @Override
    ArgumentType<A> get(@Nullable C commandBuildContext);

}
