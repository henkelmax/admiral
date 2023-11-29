package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.Min;
import de.maxhenkel.admiral.annotations.Name;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;

import java.util.Optional;

@Command("optional")
public class OptionalCommands {

    @Command
    public void range(CommandContext<CommandSourceStack> context, @Name("range") @Min("1") Optional<Double> optionalRange) {
        AdmiralMod.LOGGER.info("Optional range: {}", optionalRange.map(String::valueOf).orElse("N/A"));
    }

}
