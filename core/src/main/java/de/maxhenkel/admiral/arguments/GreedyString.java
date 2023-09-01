package de.maxhenkel.admiral.arguments;

import com.mojang.brigadier.arguments.StringArgumentType;
import de.maxhenkel.admiral.impl.arguments.StringArgumentTypeBase;

/**
 * A wrapper for {@link StringArgumentType#greedyString()}.
 */
public class GreedyString extends StringArgumentTypeBase {

    public GreedyString(String value) {
        super(value);
    }

}
