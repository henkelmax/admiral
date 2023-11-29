package de.maxhenkel.admiral.test;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import de.maxhenkel.admiral.MinecraftAdmiral;
import de.maxhenkel.admiral.argumenttype.OptionalArgumentTypeConverter;
import de.maxhenkel.admiral.test.commands.*;
import de.maxhenkel.admiral.test.types.NonWrapperDouble;
import de.maxhenkel.admiral.test.types.NonWrapperOptionalDouble;
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
            MinecraftAdmiral.builder(dispatcher, registryAccess)
                    .addCommandClasses(
                            ExampleCommands.class,
                            IntegerCommands.class,
                            PlayerCommands.class,
                            AliasCommands.class,
                            NoClassAnnotationCommands.class,
                            NoMethodAnnotationCommands.class,
                            PermissionCommands.class,
                            WrapperCommands.class,
                            LevelCommands.class,
                            ParticleCommands.class,
                            ResourceLocationCommands.class,
                            PermissionSuggestionCommands.class,
                            OptionalCommands.class
                    ).setPermissionManager((source, permission) -> {
                        if (permission.equals("admiral.test.perm1")) {
                            return true;
                        } else if (permission.equals("admiral.test.perm2")) {
                            return false;
                        } else {
                            return source.hasPermission(2);
                        }
                    })
                    .addArgumentTypes(argumentRegistry -> {
                        argumentRegistry.register(NonWrapperDouble.class, DoubleArgumentType::doubleArg, (context, value) -> new NonWrapperDouble(value));
                        argumentRegistry.register(NonWrapperOptionalDouble.class, DoubleArgumentType::doubleArg, (OptionalArgumentTypeConverter<Object, Double, NonWrapperOptionalDouble>) (context, value) -> new NonWrapperOptionalDouble(value.orElse(null)));
                    })
                    .build();
        });
    }
}
