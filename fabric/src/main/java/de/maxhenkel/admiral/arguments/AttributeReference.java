package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ReferenceBase;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class AttributeReference extends ReferenceBase<Attribute> {

    public AttributeReference(Holder.Reference<Attribute> reference) {
        super(reference);
    }

}
