package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import de.maxhenkel.admiral.annotations.Command;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import java.io.IOException;

@Command("exception")
public class ExceptionCommands {

    private static final SimpleCommandExceptionType TEST = new SimpleCommandExceptionType(Component.literal("Test"));

    @Command("command_syntax")
    public void commandSyntax(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        throw TEST.create();
    }

    @Command("runtime")
    public void runtime(CommandContext<CommandSourceStack> context) {
        throw new RuntimeException("Test");
    }

    @Command("io")
    public void io(CommandContext<CommandSourceStack> context) throws IOException {
        throw new IOException("Test");
    }

}
