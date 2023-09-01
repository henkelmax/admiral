package de.maxhenkel.admiral.test.commands;

import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import de.maxhenkel.admiral.arguments.EntityReference;
import de.maxhenkel.admiral.arguments.Word;
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
    public void test4(EntityReference ref) {
        AdmiralMod.LOGGER.info("entityreference: {}", ref);
    }

}
