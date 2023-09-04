package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import de.maxhenkel.admiral.annotations.RequiresPermission;
import de.maxhenkel.admiral.annotations.RequiresPermissionLevel;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;

@Command("permissiontest")
public class PermissionCommands {

    @Command("perm1")
    @RequiresPermission("admiral.test.perm1")
    public void perm1(CommandContext<CommandSourceStack> context, @OptionalArgument int test) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("perm1: {}", test);
    }

    @Command("perm2")
    @RequiresPermission("admiral.test.perm2")
    public void perm2(CommandContext<CommandSourceStack> context, @OptionalArgument int test) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("perm2: {}", test);
    }

    @Command("perm3")
    @RequiresPermission("admiral.test.perm3")
    public void perm3(CommandContext<CommandSourceStack> context, @OptionalArgument int test) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("perm3: {}", test);
    }

    @Command("perm4")
    @RequiresPermissionLevel(2)
    public void perm4(CommandContext<CommandSourceStack> context, @OptionalArgument int test) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("perm4: {}", test);
    }

    @Command("perm5")
    @RequiresPermissionLevel(0)
    public void perm5(CommandContext<CommandSourceStack> context, @OptionalArgument int test) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("perm5: {}", test);
    }

    @Command("test1")
    @RequiresPermission("admiral.test.perm2")
    public void test2(CommandContext<CommandSourceStack> context, int test) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("test2: {}", test);
    }

    @Command({"test1", "test2"})
    @RequiresPermission("admiral.test.perm1")
    public void test1(CommandContext<CommandSourceStack> context, int test) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("test1: {}", test);
    }

}
