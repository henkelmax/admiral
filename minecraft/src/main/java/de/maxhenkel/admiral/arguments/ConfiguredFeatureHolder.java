package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ConfiguredFeatureHolder extends SimpleArgumentType<Holder<ConfiguredFeature<?, ?>>> {

    public ConfiguredFeatureHolder(Holder<ConfiguredFeature<?, ?>> holder) {
        super(holder);
    }

}
