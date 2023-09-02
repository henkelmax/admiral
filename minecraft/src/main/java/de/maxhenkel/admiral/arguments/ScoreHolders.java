package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.ScoreHolderArgument;

/**
 * A wrapper for {@link ScoreHolderArgument#scoreHolders()}.
 */
public class ScoreHolders extends SimpleArgumentType<ScoreHolderArgument.Result> {

    public ScoreHolders(ScoreHolderArgument.Result value) {
        super(value);
    }

}
