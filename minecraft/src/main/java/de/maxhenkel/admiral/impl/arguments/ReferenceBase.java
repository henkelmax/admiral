package de.maxhenkel.admiral.impl.arguments;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

/**
 * A wrapper for {@link ResourceArgument#resource(CommandBuildContext, ResourceKey)}.
 */
public class ReferenceBase<T> {

    protected final Holder.Reference<T> reference;

    public ReferenceBase(Holder.Reference<T> reference) {
        this.reference = reference;
    }

    public Holder.Reference<T> getReference() {
        return reference;
    }

    public ResourceKey<T> key() {
        return reference.key();
    }

    public T value() {
        return reference.value();
    }

    @Override
    public String toString() {
        return reference.toString();
    }
}
