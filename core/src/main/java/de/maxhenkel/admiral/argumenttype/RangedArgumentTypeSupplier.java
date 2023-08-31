package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.arguments.ArgumentType;

import javax.annotation.Nullable;

@FunctionalInterface
public interface RangedArgumentTypeSupplier<A> extends ArgumentTypeSupplier<A> {

    ArgumentType<A> getRanged(@Nullable A min, @Nullable A max);

    @Override
    default ArgumentType<A> get() {
        return getRanged(null, null);
    }

}
