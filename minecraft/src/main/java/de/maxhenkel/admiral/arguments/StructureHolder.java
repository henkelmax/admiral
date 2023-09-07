package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.Structure;

public class StructureHolder extends SimpleArgumentType<Holder<Structure>> {

    public StructureHolder(Holder<Structure> holder) {
        super(holder);
    }

}
