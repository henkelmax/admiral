package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ResourceOrTagKeyBase;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureResourceOrTagKey extends ResourceOrTagKeyBase<Structure> {

    public StructureResourceOrTagKey(ResourceOrTagKeyArgument.Result<Structure> value) {
        super(value);
    }

}
