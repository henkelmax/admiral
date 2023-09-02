package de.maxhenkel.admiral.test.commands;

import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

@Command("resourcelocation")
public class ResourceLocationCommands {

    @Command("resourcelocation")
    public void resourcelocation(ResourceLocation value) {
        AdmiralMod.LOGGER.info("resourcelocation: {}", value);
    }

    @Command("advancement")
    public void advancement(Advancement value) {
        AdmiralMod.LOGGER.info("advancement: {}", value);
    }

    @Command("recipe")
    public void recipe(Recipe<?> value) {
        AdmiralMod.LOGGER.info("recipe: {}", value);
    }

    @Command("lootitemcondition")
    public void lootitemcondition(LootItemCondition value) {
        AdmiralMod.LOGGER.info("lootitemcondition: {}", value);
    }

    @Command("lootitemfunction")
    public void lootitemfunction(LootItemFunction value) {
        AdmiralMod.LOGGER.info("lootitemfunction: {}", value);
    }

}
