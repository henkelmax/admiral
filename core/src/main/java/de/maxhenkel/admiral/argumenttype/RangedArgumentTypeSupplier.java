package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.arguments.ArgumentType;

import javax.annotation.Nullable;

@FunctionalInterface
public interface RangedArgumentTypeSupplier<A> extends ArgumentTypeSupplier<A> {

    /**
     * @param min the minimum value or <code>null</code> if there is no minimum
     * @param max the maximum value or <code>null</code> if there is no maximum
     * @return the argument type
     */
    ArgumentType<A> getRanged(@Nullable A min, @Nullable A max);

    @Override
    default ArgumentType<A> get() {
        return getRanged(null, null);
    }

}
