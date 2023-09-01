package de.maxhenkel.admiral.arguments;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A wrapper for {@link EntityArgument#players()}.
 */
public class Players extends ArrayList<ServerPlayer> {

    public Players(Collection<ServerPlayer> players){
        super(players);
    }

}
