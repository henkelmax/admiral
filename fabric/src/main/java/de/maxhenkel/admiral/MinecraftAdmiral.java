package de.maxhenkel.admiral;

import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.admiral.impl.AdmiralImpl;
import de.maxhenkel.admiral.impl.MinecraftArgumentTypes;
import de.maxhenkel.admiral.impl.logging.MinecraftLogging;

public class MinecraftAdmiral {

    static {
        MinecraftArgumentTypes.register();
        MinecraftLogging.useMinecraftLogger();
    }

    public static <S> Builder<S> builder(CommandDispatcher<S> dispatcher) {
        return new Builder<>(dispatcher);
    }

    public static class Builder<S> extends AdmiralImpl.Builder<S> {

        protected Builder(CommandDispatcher<S> dispatcher) {
            super(dispatcher);
        }

    }

}
