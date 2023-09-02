package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ReferenceBase;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class StructureTemplatePoolReference extends ReferenceBase<StructureTemplatePool> {

    public StructureTemplatePoolReference(Holder.Reference<StructureTemplatePool> reference) {
        super(reference);
    }

}
