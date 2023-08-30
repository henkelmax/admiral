package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.annotations.*;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;

import java.util.Optional;

@Command("integer")
public class IntegerCommands {

    @Command("optionaljavainteger")
    public void optionaljavainteger(CommandContext<CommandSourceStack> context, Optional<Integer> test) {
        AdmiralMod.LOGGER.info("optionaljavainteger: {}", test.map(String::valueOf).orElse("N/A"));
    }

    @Command("optionalint")
    public void optionalint(CommandContext<CommandSourceStack> context, @OptionalArgument int test) {
        AdmiralMod.LOGGER.info("optionalint: {}", test);
    }

    @Command("optionalinteger")
    public void optionalinteger(CommandContext<CommandSourceStack> context, @OptionalArgument Integer test) {
        AdmiralMod.LOGGER.info("optionalinteger: {}", test);
    }

    @Command("intminmax")
    public void intminmax(CommandContext<CommandSourceStack> context, @MinMax(min = "10", max = "20") int test) {
        AdmiralMod.LOGGER.info("intminmax: {}", test);
    }

    @Command("intmin")
    public void intmin(CommandContext<CommandSourceStack> context, @Min("10") int test) {
        AdmiralMod.LOGGER.info("intmin: {}", test);
    }

    @Command("intmax")
    public void intmax(CommandContext<CommandSourceStack> context, @Max("20") int test) {
        AdmiralMod.LOGGER.info("intmax: {}", test);
    }

    @Command("integerminmax")
    public void integerminmax(CommandContext<CommandSourceStack> context, @MinMax(min = "10", max = "20") Integer test) {
        AdmiralMod.LOGGER.info("integerminmax: {}", test);
    }

}
