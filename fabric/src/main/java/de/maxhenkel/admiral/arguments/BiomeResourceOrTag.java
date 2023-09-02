package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ResourceOrTagBase;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.world.level.biome.Biome;

public class BiomeResourceOrTag extends ResourceOrTagBase<Biome> {

    public BiomeResourceOrTag(ResourceOrTagArgument.Result<Biome> value) {
        super(value);
    }

}
