package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ResourceOrTagBase;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureResourceOrTag extends ResourceOrTagBase<Structure> {

    public StructureResourceOrTag(ResourceOrTagLocationArgument.Result<Structure> value) {
        super(value);
    }

}
