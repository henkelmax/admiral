package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.*;
import de.maxhenkel.admiral.arguments.Player;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

@Command("playertest")
public class PlayerCommands {

    @Command("playerwrapper")
    public void playerwrapper(CommandContext<CommandSourceStack> context, Player player) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("playerwrapper: {}", player.get());
    }

    @Command("serverplayer")
    public void playerwrapper(CommandContext<CommandSourceStack> context, ServerPlayer player) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("serverplayer: {}", player);
    }

}
