package de.maxhenkel.admiral.arguments;

import net.minecraft.commands.CommandBuildContext;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A wrapper for {@link net.minecraft.commands.arguments.ResourceSelectorArgument#resourceSelector(CommandBuildContext, ResourceKey)}.
 */
public class GameTestInstances extends ArrayList<Holder.Reference<GameTestInstance>> {

    public GameTestInstances(Collection<Holder.Reference<GameTestInstance>> players) {
        super(players);
    }

}
