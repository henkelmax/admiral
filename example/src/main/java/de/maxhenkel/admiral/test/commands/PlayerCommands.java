package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.*;
import de.maxhenkel.admiral.arguments.OptionalPlayers;
import de.maxhenkel.admiral.arguments.Players;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

@Command("playertest")
public class PlayerCommands {

    @Command("serverplayer")
    public void playerwrapper(CommandContext<CommandSourceStack> context, ServerPlayer player) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("serverplayer: {}", player);
    }

    @Command("players")
    public void playerwrapper(CommandContext<CommandSourceStack> context, Players players) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("players: {}", players);
    }

    @Command("optional_players")
    public void playerwrapper(CommandContext<CommandSourceStack> context, OptionalPlayers players) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("players: {}", players);
    }

}
