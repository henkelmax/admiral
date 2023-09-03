package de.maxhenkel.admiral.test.commands;

import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.Min;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import de.maxhenkel.admiral.arguments.*;
import de.maxhenkel.admiral.test.AdmiralMod;
import de.maxhenkel.admiral.test.types.NonWrapperDouble;
import de.maxhenkel.admiral.test.types.NonWrapperOptionalDouble;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

@Command("wrapper")
public class WrapperCommands {

    @Command("optionalword")
    public void test1(@OptionalArgument Word word) {
        AdmiralMod.LOGGER.info("optionalword: {}", word);
    }

    @Command("javaoptionalword")
    public void test2(Optional<Word> word) {
        AdmiralMod.LOGGER.info("javaoptionalword: {}", word);
    }

    @Command("word")
    public void test3(Word word) {
        AdmiralMod.LOGGER.info("word: {}", word.get());
    }

    @Command("nonwrapperdouble")
    public void test4(@OptionalArgument NonWrapperDouble d) {
        AdmiralMod.LOGGER.info("nonwrapperdouble: {}", d);
    }

    @Command("nonwrapperoptionaldouble")
    public void test4(@OptionalArgument NonWrapperOptionalDouble d) {
        AdmiralMod.LOGGER.info("nonwrapperoptionaldouble: {}", d.getValue().map(String::valueOf).orElse("N/A"));
    }

    @Command("entityreference")
    public void entityreference(EntityReference ref) {
        AdmiralMod.LOGGER.info("entityreference: {}", ref);
    }

    @Command("attributereference")
    public void attributereference(AttributeReference ref) {
        AdmiralMod.LOGGER.info("attributereference: {}", ref);
    }

    @Command("configuredfeaturereference")
    public void configuredfeaturereference(ConfiguredFeatureReference ref) {
        AdmiralMod.LOGGER.info("configuredfeaturereference: {}", ref);
    }

    @Command("structurereference")
    public void structurereference(StructureReference ref) {
        AdmiralMod.LOGGER.info("structurereference: {}", ref);
    }

    @Command("structuretemplatepoolreference")
    public void structuretemplatepoolreference(StructureTemplatePoolReference ref) {
        AdmiralMod.LOGGER.info("structuretemplatepoolreference: {}", ref);
    }

    @Command("scoreboardslot")
    public void scoreboardslot(ScoreboardSlot ref) {
        AdmiralMod.LOGGER.info("scoreboardslot: {}", ref);
    }

    @Command("scoreholder")
    public void scoreholder(ScoreHolder ref) {
        AdmiralMod.LOGGER.info("scoreholder: {}", ref);
    }

    @Command("scoreholders")
    public void scoreholders(ScoreHolders ref) {
        AdmiralMod.LOGGER.info("scoreholders: {}", ref);
    }

    @Command("slot")
    public void slot(Slot ref) {
        AdmiralMod.LOGGER.info("slot: {}", ref);
    }

    @Command("team")
    public void team(Team ref) {
        AdmiralMod.LOGGER.info("team: {}", ref);
    }

    @Command("mirror")
    public void mirror(Mirror ref) {
        AdmiralMod.LOGGER.info("mirror: {}", ref);
    }

    @Command("blockrotation")
    public void blockrotation(Rotation ref) {
        AdmiralMod.LOGGER.info("blockrotation: {}", ref);
    }

    @Command("time")
    public void time(Time ref) {
        AdmiralMod.LOGGER.info("time: {}", ref);
    }

    @Command("mintime")
    public void mintime(@Min("100") Time ref) {
        AdmiralMod.LOGGER.info("mintime: {}", ref);
    }

    @Command("uuid")
    public void uuid(UUID ref) {
        AdmiralMod.LOGGER.info("uuid: {}", ref);
    }

    @Command("vec3")
    public void vec3(Vec3 ref) {
        AdmiralMod.LOGGER.info("vec3: {}", ref);
    }

    @Command("uncenteredvec3")
    public void uncenteredvec3(UncenteredVec3 ref) {
        AdmiralMod.LOGGER.info("uncenteredvec3: {}", ref);
    }

    @Command("vec2")
    public void vec2(Vec2 ref) {
        AdmiralMod.LOGGER.info("vec2: {}", ref);
    }

    @Command("uncenteredvec2")
    public void uncenteredvec2(UncenteredVec2 ref) {
        AdmiralMod.LOGGER.info("uncenteredvec2: {}", ref);
    }

    @Command("swizzle")
    public void swizzle(Swizzle ref) {
        AdmiralMod.LOGGER.info("swizzle: {}", ref);
    }

    @Command("blockpos")
    public void blockpos(BlockPos ref) {
        AdmiralMod.LOGGER.info("blockpos: {}", ref);
    }

    @Command("spawnableblockpos")
    public void spawnableblockpos(SpawnableBlockPos ref) {
        AdmiralMod.LOGGER.info("spawnableblockpos: {}", ref);
    }

    @Command("loadedblockpos")
    public void loadedblockpos(LoadedBlockPos ref) {
        AdmiralMod.LOGGER.info("loadedblockpos: {}", ref);
    }

    @Command("columnpos")
    public void columnpos(ColumnPos ref) {
        AdmiralMod.LOGGER.info("columnpos: {}", ref);
    }

    @Command("rotation")
    public void rotation(de.maxhenkel.admiral.arguments.Rotation ref) {
        AdmiralMod.LOGGER.info("rotation: {}", ref);
    }

    @Command("function")
    public void function(FunctionArgument.Result ref) {
        AdmiralMod.LOGGER.info("function: {}", ref);
    }

    @Command("iteminput")
    public void iteminput(ItemInput ref) {
        AdmiralMod.LOGGER.info("iteminput: {}", ref);
    }

}
