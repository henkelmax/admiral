package de.maxhenkel.admiral.impl.arguments;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.resources.ResourceKey;

/**
 * A wrapper for {@link ResourceOrTagArgument#resourceOrTag(CommandBuildContext, ResourceKey)}.
 */
public class ResourceOrTagBase<T> extends SimpleArgumentType<ResourceOrTagArgument.Result<T>> {

    public ResourceOrTagBase(ResourceOrTagArgument.Result<T> value) {
        super(value);
    }

}
