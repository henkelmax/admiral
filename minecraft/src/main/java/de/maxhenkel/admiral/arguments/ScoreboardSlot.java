package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.ScoreboardSlotArgument;
import net.minecraft.world.scores.DisplaySlot;

/**
 * A wrapper for {@link ScoreboardSlotArgument#displaySlot()}.
 *
 * @deprecated Use {@link DisplaySlot} instead
 */
@Deprecated
public class ScoreboardSlot extends SimpleArgumentType<Integer> {

    public ScoreboardSlot(Integer value) {
        super(value);
    }

}
