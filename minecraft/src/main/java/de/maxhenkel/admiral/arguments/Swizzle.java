package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.coordinates.SwizzleArgument;
import net.minecraft.core.Direction;

import java.util.EnumSet;

/**
 * A wrapper for {@link SwizzleArgument#swizzle()}.
 */
public class Swizzle extends SimpleArgumentType<EnumSet<Direction.Axis>> {

    public Swizzle(EnumSet<Direction.Axis> value) {
        super(value);
    }

}
