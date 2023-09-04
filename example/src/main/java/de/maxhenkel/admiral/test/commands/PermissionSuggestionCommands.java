package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.RequiresPermissionLevel;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;

@Command("suggestiontest")
public class PermissionSuggestionCommands {

    @Command("perm1")
    @RequiresPermissionLevel(1000)
    public void perm1(CommandContext<CommandSourceStack> context, int test1, int test2, int test3) {
        AdmiralMod.LOGGER.info("perm1: {}, {}, {}", test1, test2, test3);
    }

    @Command("nonperm1")
    @RequiresPermissionLevel(0)
    public void nonperm1(CommandContext<CommandSourceStack> context, int test1, int test2, int test3) {
        AdmiralMod.LOGGER.info("nonperm1: {}, {}, {}", test1, test2, test3);
    }

}
