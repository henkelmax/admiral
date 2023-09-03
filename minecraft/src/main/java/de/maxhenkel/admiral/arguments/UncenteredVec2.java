package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.world.phys.Vec2;

/**
 * A wrapper for {@link Vec2Argument#vec2(boolean)}.
 */
public class UncenteredVec2 extends SimpleArgumentType<Vec2> {

    public UncenteredVec2(Vec2 value) {
        super(value);
    }

}
