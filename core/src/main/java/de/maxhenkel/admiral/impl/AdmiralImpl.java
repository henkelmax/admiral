package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.admiral.Admiral;
import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;

public class AdmiralImpl<S, C> extends Admiral<S, C> {

    private ArgumentTypeRegistryImpl argumentRegistry;
    private CommandDispatcher<S> dispatcher;
    @Nullable
    private C commandBuildContext;
    @Nullable
    private PermissionManager<S> permissionManager;

    protected AdmiralImpl() {

    }

    public static <S, C> Builder<S, C> builder(CommandDispatcher<S> dispatcher, @Nullable C commandBuildContext) {
        return new Builder<>(dispatcher, commandBuildContext);
    }

    public ArgumentTypeRegistryImpl getArgumentRegistry() {
        return argumentRegistry;
    }

    public CommandDispatcher<S> getDispatcher() {
        return dispatcher;
    }

    @Nullable
    public C getCommandBuildContext() {
        return commandBuildContext;
    }

    @Nullable
    public PermissionManager<S> getPermissionManager() {
        return permissionManager;
    }

    public static class Builder<S, C> extends Admiral.Builder<S, C> {

        protected Builder(CommandDispatcher<S> dispatcher, @Nullable C commandBuildContext) {
            super(dispatcher, commandBuildContext);
        }

        @Override
        public AdmiralImpl<S, C> build() {
            AdmiralImpl<S, C> admiral = new AdmiralImpl<>();
            admiral.argumentRegistry = argumentRegistry;
            admiral.dispatcher = dispatcher;
            admiral.commandBuildContext = commandBuildContext;
            admiral.permissionManager = permissionManager;
            for (Class<?> c : classes) {
                AdmiralClass<S, C> admiralClass = new AdmiralClass<>(admiral, c);
                admiralClass.register();
            }
            return admiral;
        }
    }

}
