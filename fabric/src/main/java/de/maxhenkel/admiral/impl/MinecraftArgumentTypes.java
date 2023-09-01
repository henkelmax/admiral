package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.arguments.ArgumentType;
import de.maxhenkel.admiral.arguments.*;
import de.maxhenkel.admiral.argumenttype.ContextArgumentTypeSupplier;
import de.maxhenkel.admiral.argumenttype.RawArgumentTypeConverter;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.*;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

import java.util.List;

public class MinecraftArgumentTypes {

    private static boolean registered;

    public static void register() {
        if (registered) {
            return;
        }
        ArgumentTypeRegistryImpl.addRegistrations(MinecraftArgumentTypes::registerInternal);
        registered = true;
    }

    private static void registerInternal(ArgumentTypeRegistryImpl argumentRegistry) {
        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntitySelector, Entity>register(Entity.class, EntityArgument::entity, (context, value) -> value.findSingleEntity(context.getSource()));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntitySelector, ServerPlayer>register(ServerPlayer.class, EntityArgument::player, (context, value) -> value.findSinglePlayer(context.getSource()));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntitySelector, Entities>register(Entities.class, EntityArgument::entities, (context, value) -> {
            List<? extends Entity> entities = value.findEntities(context.getSource());
            if (entities.isEmpty()) {
                throw EntityArgument.NO_ENTITIES_FOUND.create();
            }
            return new Entities<>(entities);
        });
        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntitySelector, Players>register(Players.class, EntityArgument::players, (context, value) -> {
            List<ServerPlayer> players = value.findPlayers(context.getSource());
            if (players.isEmpty()) {
                throw EntityArgument.NO_PLAYERS_FOUND.create();
            }
            return new Players(players);
        });
        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntitySelector, OptionalEntities>register(OptionalEntities.class, EntityArgument::entities, (context, value) -> new OptionalEntities(value.findEntities(context.getSource())));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntitySelector, OptionalPlayers>register(OptionalPlayers.class, EntityArgument::players, (context, value) -> new OptionalPlayers(value.findPlayers(context.getSource())));

        argumentRegistry.register(AngleArgument.SingleAngle.class, AngleArgument::angle);
        argumentRegistry.register(ChatFormatting.class, ColorArgument::color);
        argumentRegistry.register(Component.class, ComponentArgument::textComponent);
        argumentRegistry.register(CompoundTag.class, CompoundTagArgument::compoundTag);
        argumentRegistry.register(EntityAnchorArgument.Anchor.class, EntityAnchorArgument::anchor);
        argumentRegistry.register(GameType.class, GameModeArgument::gameMode);
        argumentRegistry.register(GameProfileArgument.Result.class, GameProfileArgument::gameProfile);
        argumentRegistry.register(Heightmap.Types.class, HeightmapTypeArgument::heightmap);
        argumentRegistry.register(MessageArgument.Message.class, MessageArgument::message);
        argumentRegistry.register(NbtPathArgument.NbtPath.class, NbtPathArgument::nbtPath);
        argumentRegistry.register(Tag.class, NbtTagArgument::nbtTag);
        argumentRegistry.register(ObjectiveCriteria.class, ObjectiveCriteriaArgument::criteria);
        argumentRegistry.register(OperationArgument.Operation.class, OperationArgument::operation);

        argumentRegistry.register(
                ParticleOptions.class,
                (ContextArgumentTypeSupplier<CommandBuildContext, ParticleOptions>) ctx -> ctx == null ? null : ParticleArgument.particle(ctx)
        );

        argumentRegistry.<CommandSourceStack, CommandBuildContext, ResourceLocation, ServerLevel>register(
                ServerLevel.class,
                DimensionArgument::dimension,
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : DimensionArgument.getDimension(context, name)
        );
        argumentRegistry.<CommandSourceStack, CommandBuildContext, String, Objective>register(
                Objective.class,
                ObjectiveArgument::objective,
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : ObjectiveArgument.getObjective(context, name)
        );

        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntityType<?>, EntityReference>register(
                EntityReference.class,
                (ContextArgumentTypeSupplier<CommandBuildContext, EntityType<?>>) ctx -> (ArgumentType) ResourceArgument.resource(ctx, Registries.ENTITY_TYPE),
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : new EntityReference(ResourceArgument.getEntityType(context, name))
        );
    }

}
