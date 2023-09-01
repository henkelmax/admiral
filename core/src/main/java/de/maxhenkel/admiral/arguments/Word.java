package de.maxhenkel.admiral.arguments;

import com.mojang.brigadier.arguments.StringArgumentType;
import de.maxhenkel.admiral.impl.arguments.StringArgumentTypeBase;

/**
 * A wrapper for {@link StringArgumentType#word()}.
 */
public class Word extends StringArgumentTypeBase {

    public Word(String value) {
        super(value);
    }

}
