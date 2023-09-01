package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.arguments.ArgumentType;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ArgumentTypeSupplier<C, A> {

    /**
     * @return the argument type
     */
    ArgumentType<A> get();

    /**
     * @param commandBuildContext the command build context
     * @return the argument type
     */
    default ArgumentType<A> get(@Nullable C commandBuildContext) {
        return get();
    }

}
