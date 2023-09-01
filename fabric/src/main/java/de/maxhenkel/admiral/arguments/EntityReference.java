package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ReferenceBase;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;

public class EntityReference extends ReferenceBase<EntityType<?>> {

    public EntityReference(Holder.Reference<EntityType<?>> reference) {
        super(reference);
    }

}
