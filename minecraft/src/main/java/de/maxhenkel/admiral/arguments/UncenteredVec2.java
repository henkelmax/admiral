package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.world.phys.Vec2;

public class UncenteredVec2 extends SimpleArgumentType<Vec2> {

    public UncenteredVec2(Vec2 value) {
        super(value);
    }

}
