package de.maxhenkel.admiral.test.commands;

import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import de.maxhenkel.admiral.arguments.Word;
import de.maxhenkel.admiral.test.AdmiralMod;
import de.maxhenkel.admiral.test.types.NonWrapperDouble;
import de.maxhenkel.admiral.test.types.NonWrapperOptionalDouble;
import de.maxhenkel.admiral.test.types.WrapperDouble;

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

    @Command("wrapperoptionaldouble")
    public void test5(@OptionalArgument WrapperDouble d) {
        AdmiralMod.LOGGER.info("wrapperoptionaldouble: {}", d);
    }

    @Command("wrapperdouble")
    public void test6(WrapperDouble d) {
        AdmiralMod.LOGGER.info("wrapperdouble: {}", d);
    }

}
