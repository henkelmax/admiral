package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.arguments.Player;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;

@Command("class1")
@Command("class2")
public class NoMethodAnnotationCommands {

    public void player(CommandContext<CommandSourceStack> context, Player player) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("Example alias command test: {}", player.get());
    }

}
