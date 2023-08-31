package de.maxhenkel.admiral.test.commands;

import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import de.maxhenkel.admiral.arguments.Word;
import de.maxhenkel.admiral.test.AdmiralMod;

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

}
