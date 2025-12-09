package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.HexColorArgument;

/**
 * A wrapper for {@link HexColorArgument#hexColor()}.
 */
public class HexColor extends SimpleArgumentType<Integer> {

    public HexColor(Integer value) {
        super(value);
    }

}
