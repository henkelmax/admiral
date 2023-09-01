package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.arguments.ArgumentType;

import javax.annotation.Nullable;

@FunctionalInterface
public interface RangedArgumentTypeSupplier<C, A> extends ArgumentTypeSupplier<C, A> {

    /**
     * @param commandBuildContext the command build context
     * @param min                 the minimum value or <code>null</code> if there is no minimum
     * @param max                 the maximum value or <code>null</code> if there is no maximum
     * @return the argument type
     */
    default ArgumentType<A> getRanged(@Nullable C commandBuildContext, @Nullable A min, @Nullable A max) {
        return getRanged(min, max);
    }

    /**
     * @param min the minimum value or <code>null</code> if there is no minimum
     * @param max the maximum value or <code>null</code> if there is no maximum
     * @return the argument type
     */
    ArgumentType<A> getRanged(@Nullable A min, @Nullable A max);

    @Override
    default ArgumentType<A> get() {
        throw new UnsupportedOperationException();
    }

    @Override
    default ArgumentType<A> get(@Nullable C commandBuildContext) {
        return getRanged(commandBuildContext, null, null);
    }
}
