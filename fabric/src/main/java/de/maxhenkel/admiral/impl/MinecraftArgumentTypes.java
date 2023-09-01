package de.maxhenkel.admiral.impl;

import de.maxhenkel.admiral.arguments.Entities;
import de.maxhenkel.admiral.arguments.OptionalEntities;
import de.maxhenkel.admiral.arguments.OptionalPlayers;
import de.maxhenkel.admiral.arguments.Players;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.List;

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
        argumentRegistry.<CommandSourceStack, EntitySelector, Entity>register(Entity.class, EntityArgument::entity, (context, value) -> value.findSingleEntity(context.getSource()));
        argumentRegistry.<CommandSourceStack, EntitySelector, ServerPlayer>register(ServerPlayer.class, EntityArgument::player, (context, value) -> value.findSinglePlayer(context.getSource()));
        argumentRegistry.<CommandSourceStack, EntitySelector, Entities>register(Entities.class, EntityArgument::entities, (context, value) -> {
            List<? extends Entity> entities = value.findEntities(context.getSource());
            if (entities.isEmpty()) {
                throw EntityArgument.NO_ENTITIES_FOUND.create();
            }
            return new Entities<>(entities);
        });
        argumentRegistry.<CommandSourceStack, EntitySelector, Players>register(Players.class, EntityArgument::players, (context, value) -> {
            List<ServerPlayer> players = value.findPlayers(context.getSource());
            if (players.isEmpty()) {
                throw EntityArgument.NO_PLAYERS_FOUND.create();
            }
            return new Players(players);
        });
        argumentRegistry.<CommandSourceStack, EntitySelector, OptionalEntities>register(OptionalEntities.class, EntityArgument::entities, (context, value) -> new OptionalEntities(value.findEntities(context.getSource())));
        argumentRegistry.<CommandSourceStack, EntitySelector, OptionalPlayers>register(OptionalPlayers.class, EntityArgument::players, (context, value) -> new OptionalPlayers(value.findPlayers(context.getSource())));
    }

}
