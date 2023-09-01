package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.arguments.ArgumentType;

@FunctionalInterface
public interface ArgumentTypeSupplier<A> {

    /**
     * @return the argument type
     */
    ArgumentType<A> get();

}
