package de.maxhenkel.admiral.impl;

import de.maxhenkel.admiral.arguments.Players;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.server.level.ServerPlayer;

public class MinecraftArgumentTypes {

    private static boolean registered;

    public static void register() {
        if (registered) {
            return;
        }
        ArgumentTypeRegistryImpl.addRegistrations(MinecraftArgumentTypes::registerInternal);
        registered = true;
    }

    private static void registerInternal(ArgumentTypeRegistryImpl argumentRegistry) {
        argumentRegistry.<CommandSourceStack, EntitySelector, ServerPlayer>register(EntityArgument::player, (context, value) -> value.findSinglePlayer(context.getSource()), ServerPlayer.class);
        argumentRegistry.<CommandSourceStack, EntitySelector, Players>register(EntityArgument::players, (context, value) -> new Players(value.findPlayers(context.getSource())), Players.class);
    }

}
