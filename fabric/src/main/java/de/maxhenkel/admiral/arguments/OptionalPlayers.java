package de.maxhenkel.admiral.arguments;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A wrapper for {@link EntityArgument#players()}.
 * The command succeeds even if no players are found.
 */
public class OptionalPlayers extends ArrayList<ServerPlayer> {

    public OptionalPlayers(Collection<ServerPlayer> players) {
        super(players);
    }

}
