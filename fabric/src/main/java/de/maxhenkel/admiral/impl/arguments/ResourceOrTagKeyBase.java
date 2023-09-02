package de.maxhenkel.admiral.impl.arguments;

import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
import net.minecraft.resources.ResourceKey;

/**
 * A wrapper for {@link ResourceOrTagKeyArgument#resourceOrTagKey(ResourceKey)}}.
 */
public class ResourceOrTagKeyBase<T> extends SimpleArgumentType<ResourceOrTagKeyArgument.Result<T>> {

    public ResourceOrTagKeyBase(ResourceOrTagKeyArgument.Result<T> value) {
        super(value);
    }

}
