package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.phys.Vec3;

/**
 * A wrapper for {@link Vec3Argument#vec3(boolean)}.
 */
public class UncenteredVec3 extends SimpleArgumentType<Vec3> {

    public UncenteredVec3(Vec3 value) {
        super(value);
    }

}
