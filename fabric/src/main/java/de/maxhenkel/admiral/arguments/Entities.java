package de.maxhenkel.admiral.arguments;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A wrapper for {@link EntityArgument#entities()}.
 * The command fails if no entities are found.
 */
public class Entities<T extends Entity> extends ArrayList<T> {

    public Entities(Collection<T> players) {
        super(players);
    }

}
