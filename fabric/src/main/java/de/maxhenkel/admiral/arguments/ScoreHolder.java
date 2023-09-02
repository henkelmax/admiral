package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.ScoreHolderArgument;

/**
 * A wrapper for {@link ScoreHolderArgument#scoreHolder()}.
 */
public class ScoreHolder extends SimpleArgumentType<ScoreHolderArgument.Result> {

    public ScoreHolder(ScoreHolderArgument.Result value) {
        super(value);
    }

}
