package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.maxhenkel.admiral.arguments.*;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeSupplier;
import de.maxhenkel.admiral.argumenttype.ContextArgumentTypeSupplier;
import de.maxhenkel.admiral.argumenttype.RawArgumentTypeConverter;
import de.maxhenkel.admiral.impl.arguments.ReferenceBase;
import de.maxhenkel.admiral.impl.arguments.ResourceOrTagBase;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.*;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

import java.util.List;
import java.util.function.Supplier;

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
        argumentRegistry.register(ResourceLocation.class, ResourceLocationArgument::id);

        argumentRegistry.register(
                ParticleOptions.class,
                (ContextArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, ParticleOptions>) ctx -> ctx == null ? null : ParticleArgument.particle(ctx)
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
        argumentRegistry.register(
                Advancement.class,
                new ArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, ResourceLocation>() {
                    @Override
                    public ArgumentType<ResourceLocation> get() {
                        return ResourceLocationArgument.id();
                    }

                    @Override
                    public SuggestionProvider<CommandSourceStack> getSuggestionProvider() {
                        return ReflectionSuggestionProviders.getAdvancementSuggestions();
                    }
                },
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : ResourceLocationArgument.getAdvancement(context, name)
        );
        argumentRegistry.register(
                Recipe.class,
                new ArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, ResourceLocation>() {
                    @Override
                    public ArgumentType<ResourceLocation> get() {
                        return ResourceLocationArgument.id();
                    }

                    @Override
                    public SuggestionProvider<CommandSourceStack> getSuggestionProvider() {
                        return SuggestionProviders.ALL_RECIPES;
                    }
                },
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : ResourceLocationArgument.getRecipe(context, name)
        );
        argumentRegistry.register(
                LootItemCondition.class,
                new ArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, ResourceLocation>() {
                    @Override
                    public ArgumentType<ResourceLocation> get() {
                        return ResourceLocationArgument.id();
                    }

                    @Override
                    public SuggestionProvider<CommandSourceStack> getSuggestionProvider() {
                        return ReflectionSuggestionProviders.getPredicateSuggestions();
                    }
                },
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : ResourceLocationArgument.getPredicate(context, name)
        );
        argumentRegistry.register(
                LootItemFunction.class,
                new ArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, ResourceLocation>() {
                    @Override
                    public ArgumentType<ResourceLocation> get() {
                        return ResourceLocationArgument.id();
                    }

                    @Override
                    public SuggestionProvider<CommandSourceStack> getSuggestionProvider() {
                        return ReflectionSuggestionProviders.getLootItemSuggestions();
                    }
                },
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : ResourceLocationArgument.getItemModifier(context, name)
        );

        registerResourceReference(argumentRegistry, AttributeReference.class, () -> Registries.ATTRIBUTE, (ctx, name) -> new AttributeReference(ResourceArgument.getAttribute(ctx, name)));
        registerResourceKeyReference(argumentRegistry, ConfiguredFeatureReference.class, () -> Registries.CONFIGURED_FEATURE, (ctx, name) -> new ConfiguredFeatureReference(ResourceKeyArgument.getConfiguredFeature(ctx, name)));
        registerResourceKeyReference(argumentRegistry, StructureReference.class, () -> Registries.STRUCTURE, (ctx, name) -> new StructureReference(ResourceKeyArgument.getStructure(ctx, name)));
        registerResourceReference(argumentRegistry, EntityReference.class, () -> Registries.ENTITY_TYPE, (ctx, name) -> new EntityReference(ResourceArgument.getEntityType(ctx, name)));
        registerResourceReference(argumentRegistry, SummonableEntityReference.class, () -> Registries.ENTITY_TYPE, (ctx, name) -> new SummonableEntityReference(ResourceArgument.getSummonableEntityType(ctx, name)));
        registerResourceReference(argumentRegistry, MobEffectReference.class, () -> Registries.MOB_EFFECT, (ctx, name) -> new MobEffectReference(ResourceArgument.getMobEffect(ctx, name)));
        registerResourceReference(argumentRegistry, EnchantmentReference.class, () -> Registries.ENCHANTMENT, (ctx, name) -> new EnchantmentReference(ResourceArgument.getEnchantment(ctx, name)));
        registerResourceKeyReference(argumentRegistry, StructureTemplatePoolReference.class, () -> Registries.TEMPLATE_POOL, (ctx, name) -> new StructureTemplatePoolReference(ResourceKeyArgument.getStructureTemplatePool(ctx, name)));

        registerResourceOrTag(argumentRegistry, BiomeResourceOrTag.class, () -> Registries.BIOME, (ctx, name) -> new BiomeResourceOrTag(ResourceOrTagArgument.getResourceOrTag(ctx, name, Registries.BIOME)));
        registerResourceOrTag(argumentRegistry, PoiTypeResourceOrTag.class, () -> Registries.POINT_OF_INTEREST_TYPE, (ctx, name) -> new PoiTypeResourceOrTag(ResourceOrTagArgument.getResourceOrTag(ctx, name, Registries.POINT_OF_INTEREST_TYPE)));
        registerResourceOrTag(argumentRegistry, StructureResourceOrTag.class, () -> Registries.STRUCTURE, (ctx, name) -> new StructureResourceOrTag(ResourceOrTagArgument.getResourceOrTag(ctx, name, Registries.STRUCTURE)));
    }

    private static <T extends ResourceOrTagBase<R>, R> void registerResourceOrTag(ArgumentTypeRegistryImpl argumentRegistry, Class<T> clazz, Supplier<ResourceKey<Registry<R>>> registrySupplier, CommandBiFunction<CommandContext<CommandSourceStack>, String, T> referenceSupplier) {
        argumentRegistry.register(
                clazz,
                (ContextArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, R>) ctx -> (ArgumentType) ResourceOrTagArgument.resourceOrTag(ctx, registrySupplier.get()),
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : referenceSupplier.apply(context, name)
        );
    }

    private static <T extends ReferenceBase<R>, R> void registerResourceReference(ArgumentTypeRegistryImpl argumentRegistry, Class<T> clazz, Supplier<ResourceKey<Registry<R>>> registrySupplier, CommandBiFunction<CommandContext<CommandSourceStack>, String, T> referenceSupplier) {
        argumentRegistry.register(
                clazz,
                (ContextArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, R>) ctx -> (ArgumentType) ResourceArgument.resource(ctx, registrySupplier.get()),
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : referenceSupplier.apply(context, name)
        );
    }

    private static <T extends ReferenceBase<R>, R> void registerResourceKeyReference(ArgumentTypeRegistryImpl argumentRegistry, Class<T> clazz, Supplier<ResourceKey<Registry<R>>> registrySupplier, CommandBiFunction<CommandContext<CommandSourceStack>, String, T> referenceSupplier) {
        argumentRegistry.register(
                clazz,
                (ContextArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, R>) ctx -> (ArgumentType) ResourceKeyArgument.key(registrySupplier.get()),
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : referenceSupplier.apply(context, name)
        );
    }

}
