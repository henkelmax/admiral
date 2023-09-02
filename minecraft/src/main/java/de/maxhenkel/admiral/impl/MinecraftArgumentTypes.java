package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import de.maxhenkel.admiral.arguments.*;
import de.maxhenkel.admiral.argumenttype.ArgumentTypeSupplier;
import de.maxhenkel.admiral.argumenttype.ContextArgumentTypeSupplier;
import de.maxhenkel.admiral.argumenttype.RangedArgumentTypeSupplier;
import de.maxhenkel.admiral.argumenttype.RawArgumentTypeConverter;
import de.maxhenkel.admiral.impl.arguments.ReferenceBase;
import de.maxhenkel.admiral.impl.arguments.ResourceOrTagBase;
import de.maxhenkel.admiral.impl.arguments.ResourceOrTagKeyBase;
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
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

import java.util.UUID;
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
        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntitySelector, Entities>register(Entities.class, EntityArgument::entities, (RawArgumentTypeConverter) (context, name, value) -> new Entities<>(EntityArgument.getEntities(context, name)));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntitySelector, Players>register(Players.class, EntityArgument::players, (RawArgumentTypeConverter) (context, name, value) -> new Players(EntityArgument.getPlayers(context, name)));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntitySelector, OptionalEntities>register(OptionalEntities.class, EntityArgument::entities, (context, value) -> new OptionalEntities(value.findEntities(context.getSource())));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, EntitySelector, OptionalPlayers>register(OptionalPlayers.class, EntityArgument::players, (context, value) -> new OptionalPlayers(value.findPlayers(context.getSource())));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, Integer, ScoreboardSlot>register(ScoreboardSlot.class, ScoreboardSlotArgument::displaySlot, (context, value) -> new ScoreboardSlot(value));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, ScoreHolderArgument.Result, ScoreHolder>register(ScoreHolder.class, ScoreHolderArgument::scoreHolder, (context, value) -> new ScoreHolder(value));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, ScoreHolderArgument.Result, ScoreHolders>register(ScoreHolders.class, ScoreHolderArgument::scoreHolders, (context, value) -> new ScoreHolders(value));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, Integer, Slot>register(Slot.class, SlotArgument::slot, (context, value) -> new Slot(value));
        argumentRegistry.<CommandSourceStack, CommandBuildContext, String, Team>register(Team.class, TeamArgument::team, (context, value) -> new Team(value));
        argumentRegistry.register(Time.class, (RangedArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, Integer>) (min, max) -> TimeArgument.time(min == null ? 0 : min), (context, value) -> new Time(value));

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
        argumentRegistry.register(Mirror.class, TemplateMirrorArgument::templateMirror);
        argumentRegistry.register(Rotation.class, TemplateRotationArgument::templateRotation);
        argumentRegistry.register(UUID.class, UuidArgument::uuid);

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
        registerResourceReference(argumentRegistry, EntityReference.class, () -> Registries.ENTITY_TYPE, (ctx, name) -> new EntityReference(ResourceArgument.getEntityType(ctx, name)));
        registerResourceReference(argumentRegistry, SummonableEntityReference.class, () -> Registries.ENTITY_TYPE, (ctx, name) -> new SummonableEntityReference(ResourceArgument.getSummonableEntityType(ctx, name)));
        registerResourceReference(argumentRegistry, MobEffectReference.class, () -> Registries.MOB_EFFECT, (ctx, name) -> new MobEffectReference(ResourceArgument.getMobEffect(ctx, name)));
        registerResourceReference(argumentRegistry, EnchantmentReference.class, () -> Registries.ENCHANTMENT, (ctx, name) -> new EnchantmentReference(ResourceArgument.getEnchantment(ctx, name)));
        registerResourceKeyReference(argumentRegistry, ConfiguredFeatureReference.class, () -> Registries.CONFIGURED_FEATURE, (ctx, name) -> new ConfiguredFeatureReference(ResourceKeyArgument.getConfiguredFeature(ctx, name)));
        registerResourceKeyReference(argumentRegistry, StructureReference.class, () -> Registries.STRUCTURE, (ctx, name) -> new StructureReference(ResourceKeyArgument.getStructure(ctx, name)));
        registerResourceKeyReference(argumentRegistry, StructureTemplatePoolReference.class, () -> Registries.TEMPLATE_POOL, (ctx, name) -> new StructureTemplatePoolReference(ResourceKeyArgument.getStructureTemplatePool(ctx, name)));

        registerResourceOrTag(argumentRegistry, BiomeResourceOrTag.class, () -> Registries.BIOME, (ctx, name) -> new BiomeResourceOrTag(ResourceOrTagArgument.getResourceOrTag(ctx, name, Registries.BIOME)));
        registerResourceOrTag(argumentRegistry, PoiTypeResourceOrTag.class, () -> Registries.POINT_OF_INTEREST_TYPE, (ctx, name) -> new PoiTypeResourceOrTag(ResourceOrTagArgument.getResourceOrTag(ctx, name, Registries.POINT_OF_INTEREST_TYPE)));

        registerResourceOrTagKey(argumentRegistry, StructureResourceOrTagKey.class, () -> Registries.STRUCTURE, (ctx, name) -> new StructureResourceOrTagKey(ResourceOrTagKeyArgument.getResourceOrTagKey(ctx, name, Registries.STRUCTURE, DynamicExceptionTypes.ERROR_STRUCTURE_INVALID)));
    }

    private static <T extends ResourceOrTagBase<R>, R> void registerResourceOrTag(ArgumentTypeRegistryImpl argumentRegistry, Class<T> clazz, Supplier<ResourceKey<Registry<R>>> registrySupplier, CommandBiFunction<CommandContext<CommandSourceStack>, String, T> referenceSupplier) {
        argumentRegistry.register(
                clazz,
                (ContextArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, R>) ctx -> (ArgumentType) ResourceOrTagArgument.resourceOrTag(ctx, registrySupplier.get()),
                (RawArgumentTypeConverter) (context, name, value) -> value == null ? null : referenceSupplier.apply(context, name)
        );
    }

    private static <T extends ResourceOrTagKeyBase<R>, R> void registerResourceOrTagKey(ArgumentTypeRegistryImpl argumentRegistry, Class<T> clazz, Supplier<ResourceKey<Registry<R>>> registrySupplier, CommandBiFunction<CommandContext<CommandSourceStack>, String, T> referenceSupplier) {
        argumentRegistry.register(
                clazz,
                (ContextArgumentTypeSupplier<CommandSourceStack, CommandBuildContext, R>) ctx -> (ArgumentType) ResourceOrTagKeyArgument.resourceOrTagKey(registrySupplier.get()),
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