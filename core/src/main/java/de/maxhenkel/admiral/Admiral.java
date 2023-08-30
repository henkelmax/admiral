package de.maxhenkel.admiral;

import com.mojang.brigadier.CommandDispatcher;
import de.maxhenkel.admiral.impl.AdmiralClass;
import de.maxhenkel.admiral.impl.ArgumentRegistryImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class Admiral<S> {

    private final ArgumentRegistryImpl argumentRegistry;
    private final CommandDispatcher<S> dispatcher;
    private final List<AdmiralClass<S>> admiralClasses;

    private Admiral(ArgumentRegistryImpl argumentRegistry, CommandDispatcher<S> dispatcher, List<AdmiralClass<S>> admiralClasses) {
        this.argumentRegistry = argumentRegistry;
        this.dispatcher = dispatcher;
        this.admiralClasses = admiralClasses;
    }

    private static <S> Admiral<S> init(ArgumentRegistryImpl argumentRegistry, CommandDispatcher<S> dispatcher, List<Class<?>> classes) {
        List<AdmiralClass<S>> admiralClasses = new ArrayList<>();
        for (Class<?> c : classes) {
            AdmiralClass<S> admiralClass = new AdmiralClass<>(argumentRegistry, dispatcher, c);
            admiralClass.register();
            admiralClasses.add(admiralClass);
        }
        return new Admiral<>(argumentRegistry, dispatcher, admiralClasses);
    }

    public static <S> Builder<S> builder(CommandDispatcher<S> dispatcher) {
        return new Builder<>(dispatcher);
    }

    public static class Builder<S> {
        private final CommandDispatcher<S> dispatcher;
        private final ArgumentRegistryImpl argumentRegistry;
        private final List<Class<?>> classes;

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

        public Admiral<S> build() {
            return init(argumentRegistry, dispatcher, classes);
        }

    }

}
