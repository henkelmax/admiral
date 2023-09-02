package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ResourceOrTagBase;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public class PoiTypeResourceOrTag extends ResourceOrTagBase<PoiType> {

    public PoiTypeResourceOrTag(ResourceOrTagArgument.Result<PoiType> value) {
        super(value);
    }

}
