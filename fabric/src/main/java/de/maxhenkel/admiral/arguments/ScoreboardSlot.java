package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.ScoreboardSlotArgument;

/**
 * A wrapper for {@link ScoreboardSlotArgument#displaySlot()}.
 */
public class ScoreboardSlot extends SimpleArgumentType<Integer> {

    public ScoreboardSlot(Integer value) {
        super(value);
    }

}
