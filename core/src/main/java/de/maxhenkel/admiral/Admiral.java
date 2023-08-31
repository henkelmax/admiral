package de.maxhenkel.admiral;

import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.admiral.arguments.ArgumentRegistry;
import de.maxhenkel.admiral.impl.AdmiralImpl;
import de.maxhenkel.admiral.impl.ArgumentRegistryImpl;
import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * @param <S> the command source type
 */
public class Admiral<S> {

    /**
     * Creates an admiral builder <b>without</b> Minecraft specific argument types.
     *
     * @param dispatcher the command dispatcher
     * @param <S>        the command source type
     * @return an admiral builder
     */
    public static <S> Admiral.Builder<S> builder(CommandDispatcher<S> dispatcher) {
        return AdmiralImpl.builder(dispatcher);
    }

    /**
     * @param <S> the command source type
     */
    public static abstract class Builder<S> {
        protected final CommandDispatcher<S> dispatcher;
        protected final ArgumentRegistryImpl argumentRegistry;
        protected final List<Class<?>> classes;
        @Nullable
        protected PermissionManager<S> permissionManager;

        protected Builder(CommandDispatcher<S> dispatcher) {
            this.dispatcher = dispatcher;
            this.argumentRegistry = new ArgumentRegistryImpl();
            this.classes = new ArrayList<>();
        }

        /**
         * Registers a class that contains commands.
         *
         * @param classCollection a collection of classes to register
         * @return this builder
         */
        public Builder<S> addCommandClasses(Collection<Class<?>> classCollection) {
            classes.addAll(classCollection);
            return this;
        }

        /**
         * Registers one or more classes that contain commands.
         *
         * @param classArray an array of classes to register
         * @return this builder
         */
        public Builder<S> addCommandClasses(Class<?>... classArray) {
            classes.addAll(Arrays.asList(classArray));
            return this;
        }

        /**
         * @param argumentRegistryConsumer a consumer that provides an argument registry to register custom arguments
         * @return this builder
         */
        public Builder<S> addArgumentTypes(Consumer<ArgumentRegistry> argumentRegistryConsumer) {
            argumentRegistryConsumer.accept(argumentRegistry);
            return this;
        }

        /**
         * Sets the permission manager.
         *
         * @param permissionManager the permission manager
         * @return this builder
         */
        public Builder<S> setPermissionManager(PermissionManager<S> permissionManager) {
            this.permissionManager = permissionManager;
            return this;
        }

        /**
         * Builds the command tree and registers the commands.
         *
         * @return an admiral instance
         */
        public abstract Admiral<S> build();

    }

}
