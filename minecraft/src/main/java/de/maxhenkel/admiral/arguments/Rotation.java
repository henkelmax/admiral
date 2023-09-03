package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.world.phys.Vec2;

/**
 * A wrapper for {@link RotationArgument#rotation()}.
 */
public class Rotation extends SimpleArgumentType<Vec2> {

    public Rotation(Vec2 value) {
        super(value);
    }

}
