package de.maxhenkel.admiral.argumenttype;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import javax.annotation.Nullable;

@FunctionalInterface
public interface ArgumentTypeSupplier<S, C, A> {

    /**
     * @return the argument type
     */
    ArgumentType<A> get();

    /**
     * @param commandBuildContext the command build context
     * @return the argument type
     */
    default ArgumentType<A> get(@Nullable C commandBuildContext) {
        return get();
    }

    /**
     * @return the suggestion provider or <code>null</code> if there is none
     */
    default SuggestionProvider<S> getSuggestionProvider() {
        return null;
    }

}
