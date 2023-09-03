package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;

/**
 * A wrapper for {@link BlockPosArgument#blockPos()}.
 */
public class SpawnableBlockPos extends SimpleArgumentType<BlockPos> {

    public SpawnableBlockPos(BlockPos value) {
        super(value);
    }

}
