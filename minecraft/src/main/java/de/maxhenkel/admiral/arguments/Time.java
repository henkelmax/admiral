package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.TimeArgument;

/**
 * A wrapper for {@link TimeArgument#time()}.
 */
public class Time extends SimpleArgumentType<Integer> {

    public Time(Integer value) {
        super(value);
    }

}
