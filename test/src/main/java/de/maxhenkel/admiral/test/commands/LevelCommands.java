package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;

import java.util.Optional;

@Command("leveltest")
public class LevelCommands {

    @Command("level")
    public void level(CommandContext<CommandSourceStack> context, ServerLevel level) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("level: {}", level);
    }

    @Command("optional_level")
    public void optionalLevel(CommandContext<CommandSourceStack> context, @OptionalArgument ServerLevel level) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("optionalLevel: {}", level);
    }

    @Command("java_optional_level")
    public void javaOptionalLevel(CommandContext<CommandSourceStack> context, Optional<ServerLevel> level) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("level: {}", level);
    }

}
