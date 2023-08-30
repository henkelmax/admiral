package de.maxhenkel.admiral.test;

import de.maxhenkel.admiral.MinecraftAdmiral;
import de.maxhenkel.admiral.test.commands.ExampleCommands;
import de.maxhenkel.admiral.test.commands.IntegerCommands;
import de.maxhenkel.admiral.test.commands.PlayerCommands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdmiralMod implements ModInitializer {

    public static final String MODID = "admiral_test";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Admiral test mod");
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            MinecraftAdmiral.builder(dispatcher).addCommandClasses(ExampleCommands.class, IntegerCommands.class, PlayerCommands.class).build();
        });
    }
}
