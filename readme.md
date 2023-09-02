# Admiral

An annotation based command handling library for Minecraft.

## Supported Argument Types

### Core

| Class                     | Argument Type                      | Comment                    |
|---------------------------|------------------------------------|----------------------------|
| `String`                  | `StringArgumentType::string`       |                            |
| `GreedyString`            | `StringArgumentType::greedyString` |                            |
| `Word`                    | `StringArgumentType::word`         |                            |
| `Long` <br/> `long`       | `LongArgumentType::longArg`        | Can have `@Min` and `@Max` |
| `Integer` <br/> `int`     | `IntegerArgumentType::integer`     | Can have `@Min` and `@Max` |
| `Double` <br/> `double`   | `DoubleArgumentType::doubleArg`    | Can have `@Min` and `@Max` |
| `Float` <br/> `float`     | `FloatArgumentType::floatArg`      | Can have `@Min` and `@Max` |
| `Boolean` <br/> `boolean` | `BoolArgumentType::bool`           |                            |

### Minecraft

| Class                            | Argument Type                                | Comment                      |
|----------------------------------|----------------------------------------------|------------------------------|
| `Entity`                         | `EntityArgument::entity`                     |                              |
| `ServerPlayer`                   | `EntityArgument::player`                     |                              |
| `Entities`                       | `EntityArgument::entities`                   | Fails if no entity was found |
| `Players`                        | `EntityArgument::players`                    | Fails if no player was found |
| `OptionalEntities`               | `EntityArgument::entities`                   |                              |
| `OptionalPlayers`                | `EntityArgument::players`                    |                              |
| `ScoreboardSlot`                 | `ScoreboardSlotArgument::displaySlot`        |                              |
| `ScoreHolder`                    | `ScoreHolderArgument::scoreHolder`           |                              |
| `ScoreHolders`                   | `ScoreHolderArgument::scoreHolders`          |                              |
| `Slot`                           | `SlotArgument::slot`                         |                              |
| `Team`                           | `TeamArgument::team`                         |                              |
| `Time`                           | `TimeArgument::time`                         | Can have `@Min`              |
| `AngleArgument.SingleAngle`      | `AngleArgument::angle`                       |                              |
| `ChatFormatting`                 | `ColorArgument::color`                       |                              |
| `Component`                      | `ComponentArgument::textComponent`           |                              |
| `CompoundTag`                    | `CompoundTagArgument::compoundTag`           |                              |
| `EntityAnchorArgument.Anchor`    | `EntityAnchorArgument::anchor`               |                              |
| `GameType`                       | `GameModeArgument::gameMode`                 |                              |
| `GameProfileArgument.Result`     | `GameProfileArgument::gameProfile`           |                              |
| `Heightmap.Types`                | `HeightmapTypeArgument::heightmap`           |                              |
| `MessageArgument.Message`        | `MessageArgument::message`                   |                              |
| `NbtPathArgument.NbtPath`        | `NbtPathArgument::nbtPath`                   |                              |
| `Tag`                            | `NbtTagArgument::nbtTag`                     |                              |
| `ObjectiveCriteria`              | `ObjectiveCriteriaArgument::criteria`        |                              |
| `OperationArgument.Operation`    | `OperationArgument::operation`               |                              |
| `ResourceLocation`               | `ResourceLocationArgument::id`               |                              |
| `Mirror`                         | `TemplateMirrorArgument::templateMirror`     |                              |
| `Rotation`                       | `TemplateRotationArgument::templateRotation` |                              |
| `UUID`                           | `UuidArgument::uuid`                         |                              |
| `ParticleOptions`                | `ParticleArgument::particle`                 |                              |
| `ServerLevel`                    | `DimensionArgument::dimension`               |                              |
| `Objective`                      | `ObjectiveArgument::objective`               |                              |
| `Advancement`                    | `ResourceLocationArgument::id`               |                              |
| `Recipe`                         | `ResourceLocationArgument::id`               |                              |
| `LootItemCondition`              | `ResourceLocationArgument::id`               |                              |
| `LootItemFunction`               | `ResourceLocationArgument::id`               |                              |
| `AttributeReference`             | `ResourceOrTagArgument::resourceOrTag`       |                              |
| `EntityReference`                | `ResourceOrTagArgument::resourceOrTag`       |                              |
| `SummonableEntityReference`      | `ResourceOrTagArgument::resourceOrTag`       |                              |
| `MobEffectReference`             | `ResourceOrTagArgument::resourceOrTag`       |                              |
| `EnchantmentReference`           | `ResourceOrTagArgument::resourceOrTag`       |                              |
| `ConfiguredFeatureReference`     | `ResourceKeyArgument::key`                   |                              |
| `StructureReference`             | `ResourceKeyArgument::key`                   |                              |
| `StructureTemplatePoolReference` | `ResourceKeyArgument::key`                   |                              |
| `BiomeResourceOrTag`             | `ResourceOrTagArgument::resourceOrTag`       |                              |
| `PoiTypeResourceOrTag`           | `ResourceOrTagArgument::resourceOrTag`       |                              |
| `StructureResourceOrTagKey`      | `ResourceOrTagKeyArgument::resourceOrTagKey` |                              |
