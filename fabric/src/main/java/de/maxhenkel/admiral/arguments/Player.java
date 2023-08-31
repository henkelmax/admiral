package de.maxhenkel.admiral.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeWrapper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.server.level.ServerPlayer;

/**
 * A wrapper for {@link EntityArgument#player()}.
 */
public class Player extends ArgumentTypeWrapper<CommandSourceStack, EntitySelector, ServerPlayer> {

    @Override
    protected ServerPlayer convert(CommandContext<CommandSourceStack> context, EntitySelector value) throws CommandSyntaxException {
        return value.findSinglePlayer(context.getSource());
    }

    @Override
    protected Class<EntitySelector> getArgumentTypeClass() {
        return EntitySelector.class;
    }

    @Override
    protected ArgumentType<EntitySelector> getArgumentType() {
        return EntityArgument.player();
    }

}
