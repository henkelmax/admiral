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

    /**
     * Creates an admiral builder with Minecraft specific argument types.
     * Always use this method if you want to use Minecraft specific argument types.
     *
     * @param dispatcher the command dispatcher
     * @param <S>        the command source type
     * @return an admiral builder
     */
    public static <S> Builder<S> builder(CommandDispatcher<S> dispatcher) {
        return new Builder<>(dispatcher);
    }

    /**
     * @param <S> the command source type
     */
    public static class Builder<S> extends AdmiralImpl.Builder<S> {

        protected Builder(CommandDispatcher<S> dispatcher) {
            super(dispatcher);
        }

    }

}
