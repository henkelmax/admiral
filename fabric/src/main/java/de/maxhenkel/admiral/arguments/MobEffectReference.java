package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ReferenceBase;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

public class MobEffectReference extends ReferenceBase<MobEffect> {

    public MobEffectReference(Holder.Reference<MobEffect> reference) {
        super(reference);
    }

}
