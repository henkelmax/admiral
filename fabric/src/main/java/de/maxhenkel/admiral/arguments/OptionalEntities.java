package de.maxhenkel.admiral.arguments;

import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A wrapper for {@link EntityArgument#entities()}.
 * The command succeeds even if no entities are found.
 */
public class OptionalEntities<T extends Entity> extends ArrayList<T> {

    public OptionalEntities(Collection<T> players) {
        super(players);
    }

}
