package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

@Command("class1")
@Command("class2")
public class NoMethodAnnotationCommands {

    @Command
    public void player(CommandContext<CommandSourceStack> context, ServerPlayer player) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("Example alias command test: {}", player);
    }

}
