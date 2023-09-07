package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ResourceOrTagBase;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class PoiTypeResourceOrTag extends ResourceOrTagBase<PoiType> {

    public PoiTypeResourceOrTag(ResourceOrTagLocationArgument.Result<PoiType> value) {
        super(value);
    }

}
