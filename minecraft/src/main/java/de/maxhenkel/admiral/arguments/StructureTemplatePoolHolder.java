package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class StructureTemplatePoolHolder extends SimpleArgumentType<Holder<StructureTemplatePool>> {

    public StructureTemplatePoolHolder(Holder<StructureTemplatePool> holder) {
        super(holder);
    }

}
