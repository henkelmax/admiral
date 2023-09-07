package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.world.entity.EntityType;

public class SummonableEntity extends SimpleArgumentType<EntityType<?>> {

    public SummonableEntity(EntityType<?> value) {
        super(value);
    }

}
