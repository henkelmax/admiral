package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ReferenceBase;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ConfiguredFeatureReference extends ReferenceBase<ConfiguredFeature<?, ?>> {

    public ConfiguredFeatureReference(Holder.Reference<ConfiguredFeature<?, ?>> reference) {
        super(reference);
    }

}
