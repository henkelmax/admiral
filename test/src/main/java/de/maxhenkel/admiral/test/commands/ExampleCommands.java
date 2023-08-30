package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.Name;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;

import java.util.Optional;

@Command("example")
public class ExampleCommands {

    @Command({"abc", "def"})
    public void example(CommandContext<CommandSourceStack> context, @Name("test1") String test1, @OptionalArgument @Name("test2") String test2, @OptionalArgument @Name("test3") String test3) {
        AdmiralMod.LOGGER.info("Example command: {}, {}, {}", test1, test2, test3);
    }

    @Command("test")
    public void example2(CommandContext<CommandSourceStack> context, @OptionalArgument @Name("test") String test) {
        AdmiralMod.LOGGER.info("Example2 command: {}", test);
    }

    @Command("qq")
    public void example4(CommandContext<CommandSourceStack> context, @Name("test") Optional<String> test) {
        AdmiralMod.LOGGER.info("Example4 command: {}", test.orElse("N/A"));
    }

    @Command
    public void example3(CommandContext<CommandSourceStack> context) {
        AdmiralMod.LOGGER.info("Example3 command");
    }

    @Command("test")
    public void ovlerload1(CommandContext<CommandSourceStack> context, @Name("test") String test) {
        AdmiralMod.LOGGER.info("overload command: {}", test);
    }

    @Command("test")
    public void ovlerload2(CommandContext<CommandSourceStack> context, @Name("test") String test, @OptionalArgument @Name("test2") String test2) {
        AdmiralMod.LOGGER.info("Overload command: {}, {}", test, test2);
    }

}
