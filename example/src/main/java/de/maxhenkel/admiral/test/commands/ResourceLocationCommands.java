package de.maxhenkel.admiral.test.commands;

import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.arguments.BiomeResourceOrTag;
import de.maxhenkel.admiral.arguments.PoiTypeResourceOrTag;
import de.maxhenkel.admiral.arguments.StructureResourceOrTagKey;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

@Command("resourcelocation")
public class ResourceLocationCommands {

    @Command("resourcelocation")
    public void resourcelocation(ResourceLocation value) {
        AdmiralMod.LOGGER.info("resourcelocation: {}", value);
    }

    @Command("advancement")
    public void advancement(AdvancementHolder value) {
        AdmiralMod.LOGGER.info("advancement: {}", value);
    }

    @Command("recipe")
    public void recipe(RecipeHolder<?> value) {
        AdmiralMod.LOGGER.info("recipe: {}", value);
    }

    @Command("biomeresourceortag")
    public void biomeresourceortag(BiomeResourceOrTag value) {
        AdmiralMod.LOGGER.info("biomeresourceortag: {}", value);
    }

    @Command("poityperesourceortag")
    public void poityperesourceortag(PoiTypeResourceOrTag value) {
        AdmiralMod.LOGGER.info("poityperesourceortag: {}", value);
    }

    @Command("structureresourceortagkey")
    public void structureresourceortagkey(StructureResourceOrTagKey value) {
        AdmiralMod.LOGGER.info("structureresourceortagkey: {}", value);
    }

    @Command("loottable")
    public void loottable(LootTable value) {
        AdmiralMod.LOGGER.info("loottable: {}", value);
    }

    @Command("lootitemfunction")
    public void lootitemfunction(LootItemFunction value) {
        AdmiralMod.LOGGER.info("lootitemfunction: {}", value);
    }

    @Command("lootitemcondition")
    public void lootitemcondition(LootItemCondition value) {
        AdmiralMod.LOGGER.info("lootitemcondition: {}", value);
    }

}
