package de.maxhenkel.admiral;

import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.admiral.impl.*;
import net.minecraft.commands.CommandBuildContext;

public class MinecraftAdmiral {

    static {
        PermissionLevelManager.setPermissionLevelAccessor(new MinecraftPermissionLevelAccessor<>());
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
    public static <S> Builder<S> builder(CommandDispatcher<S> dispatcher, CommandBuildContext commandBuildContext) {
        return new Builder<>(dispatcher, commandBuildContext);
    }

    /**
     * @param <S> the command source type
     */
    public static class Builder<S> extends AdmiralImpl.Builder<S, CommandBuildContext> {

        protected Builder(CommandDispatcher<S> dispatcher, CommandBuildContext commandBuildContext) {
            super(dispatcher, commandBuildContext);
        }

    }

}
