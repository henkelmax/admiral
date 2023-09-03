package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;

/**
 * A wrapper for {@link BlockPosArgument#blockPos()}.
 */
public class LoadedBlockPos extends SimpleArgumentType<BlockPos> {

    public LoadedBlockPos(BlockPos value) {
        super(value);
    }

}
