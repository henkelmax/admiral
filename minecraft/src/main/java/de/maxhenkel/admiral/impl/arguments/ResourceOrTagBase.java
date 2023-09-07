package de.maxhenkel.admiral.impl.arguments;

import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.resources.ResourceKey;

/**
 * A wrapper for {@link ResourceOrTagLocationArgument#resourceOrTag(ResourceKey)}.
 */
public class ResourceOrTagBase<T> extends SimpleArgumentType<ResourceOrTagLocationArgument.Result<T>> {

    public ResourceOrTagBase(ResourceOrTagLocationArgument.Result<T> value) {
        super(value);
    }

}
