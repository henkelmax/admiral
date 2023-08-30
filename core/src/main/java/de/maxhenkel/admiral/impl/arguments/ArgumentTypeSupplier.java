package de.maxhenkel.admiral.impl.arguments;

import com.mojang.brigadier.arguments.ArgumentType;

public interface ArgumentTypeSupplier<A> {

    ArgumentType<A> get();

}
