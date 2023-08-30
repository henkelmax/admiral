package de.maxhenkel.admiral.impl.arguments;

import com.mojang.brigadier.arguments.ArgumentType;

@FunctionalInterface
public interface MinArgumentTypeSupplier<A> {

    ArgumentType<A> get(A min);

}
