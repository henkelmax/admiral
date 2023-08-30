package de.maxhenkel.admiral.impl;

import de.maxhenkel.admiral.arguments.Player;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;

public class MinecraftArgumentTypes {

    private static boolean registered;

    public static void register() {
        if (registered) {
            return;
        }
        ArgumentRegistryImpl.addRegistrations(MinecraftArgumentTypes::registerInternal);
        registered = true;
    }

    private static void registerInternal(ArgumentRegistryImpl argumentRegistry) {
        argumentRegistry.register(EntityArgument::player, EntitySelector.class, Player.class);
    }

}
