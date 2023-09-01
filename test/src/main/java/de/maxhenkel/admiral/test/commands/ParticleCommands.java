package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.particles.ParticleOptions;

@Command("particletest")
public class ParticleCommands {

    @Command("particle")
    public void particle(CommandContext<CommandSourceStack> context, ParticleOptions particleOptions) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("particle: {}", particleOptions);
    }

}
