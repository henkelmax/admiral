package de.maxhenkel.admiral.test.commands;

import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import de.maxhenkel.admiral.arguments.*;
import de.maxhenkel.admiral.test.AdmiralMod;
import de.maxhenkel.admiral.test.types.NonWrapperDouble;
import de.maxhenkel.admiral.test.types.NonWrapperOptionalDouble;

import java.util.Optional;

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

}
