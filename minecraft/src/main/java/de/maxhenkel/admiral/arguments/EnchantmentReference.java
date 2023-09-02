package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.ReferenceBase;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentReference extends ReferenceBase<Enchantment> {

    public EnchantmentReference(Holder.Reference<Enchantment> reference) {
        super(reference);
    }

}
