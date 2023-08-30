package de.maxhenkel.admiral;

import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.admiral.impl.MinecraftArgumentTypes;

public class MinecraftAdmiral {

    public static <S> Builder<S> builder(CommandDispatcher<S> dispatcher) {
        return new Builder<>(dispatcher);
    }

    public static class Builder<S> extends Admiral.Builder<S> {

        protected Builder(CommandDispatcher<S> dispatcher) {
            super(dispatcher);
            MinecraftArgumentTypes.register();
        }

    }

}
