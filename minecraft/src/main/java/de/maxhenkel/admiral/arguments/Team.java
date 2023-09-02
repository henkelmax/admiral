package de.maxhenkel.admiral.arguments;

import de.maxhenkel.admiral.impl.arguments.SimpleArgumentType;
import net.minecraft.commands.arguments.TeamArgument;

/**
 * A wrapper for {@link TeamArgument#team()}.
 */
public class Team extends SimpleArgumentType<String> {

    public Team(String value) {
        super(value);
    }

}
