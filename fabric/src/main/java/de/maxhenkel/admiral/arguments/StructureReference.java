package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ReferenceBase;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureReference extends ReferenceBase<Structure> {

    public StructureReference(Holder.Reference<Structure> reference) {
        super(reference);
    }

}
