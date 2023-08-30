package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.*;
import de.maxhenkel.admiral.arguments.Player;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;

@Command("playertest")
public class PlayerCommands {

    @Command("player")
    public void player(CommandContext<CommandSourceStack> context, Player player) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("Example command test: {}", player.get());
    }

}
