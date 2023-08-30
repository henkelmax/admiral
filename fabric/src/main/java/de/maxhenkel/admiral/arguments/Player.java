package de.maxhenkel.admiral.arguments;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.server.level.ServerPlayer;

public class Player extends ArgumentWrapper<CommandSourceStack, EntitySelector, ServerPlayer> {

    public Player(CommandContext<CommandSourceStack> context, EntitySelector value) {
        super(context, value);
    }

    @Override
    public ServerPlayer convert(EntitySelector value) throws CommandSyntaxException {
        if (context == null) {
            throw new IllegalStateException("Context is null");
        }
        return value.findSinglePlayer(context.getSource());
    }
}
