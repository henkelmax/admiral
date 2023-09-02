package de.maxhenkel.admiral.arguments;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * A wrapper for {@link ResourceLocationArgument#getPredicate(CommandContext, String)}.
 */
public class Predicate extends SimpleArgumentType<LootItemCondition> {

    public Predicate(LootItemCondition value) {
        super(value);
    }

}
