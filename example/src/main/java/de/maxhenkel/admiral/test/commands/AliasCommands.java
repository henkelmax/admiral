package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

@Command("aliastest1")
@Command("aliastest2")
public class AliasCommands {

    @Command("player1")
    @Command("player2")
    public void player(CommandContext<CommandSourceStack> context, ServerPlayer player) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("Example alias command test: {}", player);
    }

}
