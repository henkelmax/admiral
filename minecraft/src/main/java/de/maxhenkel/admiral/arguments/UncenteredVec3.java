package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.world.phys.Vec3;

public class UncenteredVec3 extends SimpleArgumentType<Vec3> {

    public UncenteredVec3(Vec3 value) {
        super(value);
    }

}
