package de.maxhenkel.admiral;

import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.admiral.impl.ArgumentRegistryImpl;
import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class Admiral<S> {

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

        public Builder<S> addCommandClasses(Collection<Class<?>> classCollection) {
            classes.addAll(classCollection);
            return this;
        }

        public Builder<S> addCommandClasses(Class<?>... classArray) {
            classes.addAll(Arrays.asList(classArray));
            return this;
        }

        public Builder<S> addArgumentTypes(Consumer<ArgumentRegistry> argumentRegistryConsumer) {
            argumentRegistryConsumer.accept(argumentRegistry);
            return this;
        }

        public Builder<S> setPermissionManager(PermissionManager<S> permissionManager) {
            this.permissionManager = permissionManager;
            return this;
        }

        public abstract Admiral<S> build();

    }

}
