package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.SlotArgument;

/**
 * A wrapper for {@link SlotArgument#slot()}.
 */
public class Slot extends SimpleArgumentType<Integer> {

    public Slot(Integer value) {
        super(value);
    }

}
