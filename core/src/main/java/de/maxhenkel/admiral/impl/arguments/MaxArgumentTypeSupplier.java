package de.maxhenkel.admiral.impl.arguments;

import com.mojang.brigadier.arguments.ArgumentType;

public interface MaxArgumentTypeSupplier<A> {

    ArgumentType<A> get(A max);

}
