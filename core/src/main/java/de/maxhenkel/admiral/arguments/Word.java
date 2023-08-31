package de.maxhenkel.admiral.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import de.maxhenkel.admiral.argumenttype.StringArgumentTypeBase;

/**
 * A wrapper for {@link StringArgumentType#word()}.
 */
public class Word extends StringArgumentTypeBase {

    @Override
    protected ArgumentType<String> getArgumentType() {
        return StringArgumentType.word();
    }

}
