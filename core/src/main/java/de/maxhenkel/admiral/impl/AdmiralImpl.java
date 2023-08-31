package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.admiral.Admiral;
import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;

public class AdmiralImpl<S> extends Admiral<S> {

    private ArgumentTypeRegistryImpl argumentRegistry;
    private CommandDispatcher<S> dispatcher;
    @Nullable
    private PermissionManager<S> permissionManager;

    protected AdmiralImpl() {

    }

    public static <S> Builder<S> builder(CommandDispatcher<S> dispatcher) {
        return new Builder<>(dispatcher);
    }

    public ArgumentTypeRegistryImpl getArgumentRegistry() {
        return argumentRegistry;
    }

    public CommandDispatcher<S> getDispatcher() {
        return dispatcher;
    }

    @Nullable
    public PermissionManager<S> getPermissionManager() {
        return permissionManager;
    }

    public static class Builder<S> extends Admiral.Builder<S> {
        protected Builder(CommandDispatcher<S> dispatcher) {
            super(dispatcher);
        }

        @Override
        public AdmiralImpl<S> build() {
            AdmiralImpl<S> admiral = new AdmiralImpl<>();
            admiral.argumentRegistry = argumentRegistry;
            admiral.dispatcher = dispatcher;
            admiral.permissionManager = permissionManager;
            for (Class<?> c : classes) {
                AdmiralClass<S> admiralClass = new AdmiralClass<>(admiral, c);
                admiralClass.register();
            }
            return admiral;
        }
    }

}
