⚠️ **Warning**
This project is still in early development and might contain bugs.
Please report any issues you find.

# Admiral

An open-source annotation based command handling library for [Brigadier](https://github.com/Mojang/brigadier).

This project contains four different modules:

- `admiral-core` The core module containing the annotation processor and all built-in argument types from [Brigadier](https://github.com/Mojang/brigadier).
- `admiral-minecraft` A module containing all Minecraft-specific argument types, compiled with official Minecraft mappings.
- `admiral-fabric` A module containing everything from the `core` and `minecraft` modules, compiled with Fabric intermediary mappings.
- `admiral-forge` A module containing everything from the `core` and `minecraft` modules, compiled with SRG mappings.

## Links

- [Modrinth](https://modrinth.com/mod/admiral)
- [Issues](https://github.com/henkelmax/admiral/issues)
- [License](LICENSE)

## Supported versions

- `1.21.2`, `1.21.3` (Branch [master](https://github.com/henkelmax/admiral/tree/master))
- `1.20.5`, `1.20.6`, `1.21`, `1.21.1` (Branch [1.21.1](https://github.com/henkelmax/admiral/tree/1.21.1))
- `1.20.3`, `1.20.4` (Branch [1.20.4](https://github.com/henkelmax/admiral/tree/1.20.4))
- `1.20.2` (Branch [1.20.2](https://github.com/henkelmax/admiral/tree/1.20.2))
- `1.19.4`, `1.20` and `1.20.1` (Branch [1.20.1](https://github.com/henkelmax/admiral/tree/1.20.1))
- `1.19`, `1.19.1` and `1.19.2` (Branch [1.19.2](https://github.com/henkelmax/admiral/tree/1.19.2))

## Usage

Registering your command class:

``` java
CommandDispatcher<CommandSourceStack> dispatcher = ...;
CommandBuildContext commandBuildContext  = ...;

MinecraftAdmiral.builder(dispatcher, commandBuildContext).addCommandClasses(MyCommands.class).build();
```

Defining your command class:

``` java
@Command("example")
public class MyCommands {
    /*
     * Is executed when the command "/example string <name>" is executed.
     * The name argument is required and has to be a string.
     * The context parameter is optional.
     */
    @Command("string")
    public int string(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
        System.out.println(name);
        return 1;
    }

    /*
     * Is executed when the command "/example player <player>" is executed.
     * If the method doesn't return an int or Integer, the command will return 1 by default if executed successfully.
     * The @Name annotation is optional and can be used to specify the name of the argument. Defaults to "arg0", "arg1", ...
     */
    @Command("player")
    public void player(@Name("player") ServerPlayer player) {
        System.out.println(player.getName().getString());
    }

    /*
     * Is executed when the command "/example <number> <longNumber>" is executed.
     * The number argument is required and has to be an integer between 10 and 20.
     * The longNumber argument is required and has to be a long between 0 and Long.MAX_VALUE.
     * The optionalDouble argument is optional and has to be a double.
     * The bool argument is optional and has to be a boolean.
     * After using an optional argument, all following arguments have to be optional as well.
     */
    @Command()
    public void example(@MinMax(min = "10", max = "20") int number, @Min("0") long longNumber, Optional<Double> optionalDouble, @OptionalArgument Boolean bool) {
        System.out.println(number);
        System.out.println(longNumber);
        System.out.println(optionalDouble.orElse(0D));
        if (bool != null) {
            System.out.println(bool);
        }
    }

    /*
     * Is executed when the command "/example admin <nbt>" is executed.
     * This command can only be executed by players with permission level 4 (OPs).
     */
    @Command("admin")
    @RequiresPermissionLevel(4)
    public void admin(CompoundTag nbt) {
        System.out.println(nbt.toString());
    }

    /*
     * Is executed when the command "/example permission test" is executed.
     * This command can only be executed by players with the permission node "admiral.test.perm1".
     * You need to set a permission manager in the Admiral builder for this to work.
     */
    @Command({"permission", "test"})
    @RequiresPermission("admiral.test.perm1")
    public void permission() {
        System.out.println("This command can only be executed by players with the permission 'admiral.test.perm1'");
    }
}
```

## Setup

It is generally recommended to use [Shadow](https://github.com/johnrengelman/shadow) to shade Admiral into your mod.
This avoids conflicts with other mods using different versions of Admiral.


Alternatively use the following setup to include Admiral in your mod.

### Fabric

``` gradle
repositories {
    // See https://docs.modrinth.com/maven
    maven {
        url = 'https://api.modrinth.com/maven'
        content {
            includeGroup "maven.modrinth"
        }
    }
}

dependencies {
    include(modImplementation("maven.modrinth:admiral:${admiral_version}+${minecraft_version}+fabric"))
}
```

### Forge


Alternatively you can use [JarJar](https://docs.minecraftforge.net/en/fg-6.x/dependencies/jarinjar/) to include the Admiral jar in your mod:
``` gradle
jarJar.enable()

repositories {
    // See https://docs.modrinth.com/maven
    maven {
        url = 'https://api.modrinth.com/maven'
        content {
            includeGroup 'maven.modrinth'
        }
    }
}

dependencies {
    jarJar(group: 'maven.modrinth', name: 'admiral', version: "${admiral_version}+${minecraft_version}+forge")
}
```

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

| Class                            | Argument Type                                | Comment                                |
|----------------------------------|----------------------------------------------|----------------------------------------|
| `Entity`                         | `EntityArgument::entity`                     |                                        |
| `ServerPlayer`                   | `EntityArgument::player`                     |                                        |
| `Entities`                       | `EntityArgument::entities`                   | Fails if no entity was found           |
| `Players`                        | `EntityArgument::players`                    | Fails if no player was found           |
| `OptionalEntities`               | `EntityArgument::entities`                   |                                        |
| `OptionalPlayers`                | `EntityArgument::players`                    |                                        |
| `ScoreboardSlot`                 | `ScoreboardSlotArgument::displaySlot`        |                                        |
| `ScoreHolder`                    | `ScoreHolderArgument::scoreHolder`           |                                        |
| `ScoreHolders`                   | `ScoreHolderArgument::scoreHolders`          |                                        |
| `Slot`                           | `SlotArgument::slot`                         |                                        |
| `Team`                           | `TeamArgument::team`                         |                                        |
| `Time`                           | `TimeArgument::time`                         | Can have `@Min`                        |
| `BlockPos`                       | `BlockPosArgument::blockPos`                 |                                        |
| `LoadedBlockPos`                 | `BlockPosArgument::blockPos`                 |                                        |
| `SpawnableBlockPos`              | `BlockPosArgument::blockPos`                 |                                        |
| `ColumnPos`                      | `ColumnPosArgument::columnPos`               |                                        |
| `Rotation`                       | `RotationArgument::rotation`                 |                                        |
| `Swizzle`                        | `SwizzleArgument::swizzle`                   |                                        |
| `Vec2`                           | `Vec2Argument::vec2`                         |                                        |
| `UncenteredVec2`                 | `Vec2Argument::vec2`                         | Does not center the X and Z coordinate |
| `Vec3`                           | `Vec3Argument::vec3`                         |                                        |
| `UncenteredVec3`                 | `Vec3Argument::vec3`                         | Does not center the X and Z coordinate |
| `AngleArgument.SingleAngle`      | `AngleArgument::angle`                       |                                        |
| `ChatFormatting`                 | `ColorArgument::color`                       |                                        |
| `Component`                      | `ComponentArgument::textComponent`           |                                        |
| `CompoundTag`                    | `CompoundTagArgument::compoundTag`           |                                        |
| `EntityAnchorArgument.Anchor`    | `EntityAnchorArgument::anchor`               |                                        |
| `GameType`                       | `GameModeArgument::gameMode`                 |                                        |
| `GameProfileArgument.Result`     | `GameProfileArgument::gameProfile`           |                                        |
| `Heightmap.Types`                | `HeightmapTypeArgument::heightmap`           |                                        |
| `MessageArgument.Message`        | `MessageArgument::message`                   |                                        |
| `NbtPathArgument.NbtPath`        | `NbtPathArgument::nbtPath`                   |                                        |
| `Tag`                            | `NbtTagArgument::nbtTag`                     |                                        |
| `ObjectiveCriteria`              | `ObjectiveCriteriaArgument::criteria`        |                                        |
| `OperationArgument.Operation`    | `OperationArgument::operation`               |                                        |
| `ResourceLocation`               | `ResourceLocationArgument::id`               |                                        |
| `Mirror`                         | `TemplateMirrorArgument::templateMirror`     |                                        |
| `Rotation`                       | `TemplateRotationArgument::templateRotation` |                                        |
| `UUID`                           | `UuidArgument::uuid`                         |                                        |
| `FunctionArgument.Result`        | `FunctionArgument::functions`                |                                        |
| `BlockPredicateArgument.Result`  | `BlockPredicateArgument::blockPredicate`     |                                        |
| `BlockInput`                     | `BlockStateArgument::block`                  |                                        |
| `ParticleOptions`                | `ParticleArgument::particle`                 |                                        |
| `ItemInput`                      | `ItemArgument::item`                         |                                        |
| `ItemPredicateArgument.Result`   | `ItemPredicateArgument::itemPredicate`       |                                        |
| `ServerLevel`                    | `DimensionArgument::dimension`               |                                        |
| `Objective`                      | `ObjectiveArgument::objective`               |                                        |
| `Advancement`                    | `ResourceLocationArgument::id`               |                                        |
| `Recipe`                         | `ResourceLocationArgument::id`               |                                        |
| `LootItemCondition`              | `ResourceLocationArgument::id`               |                                        |
| `LootItemFunction`               | `ResourceLocationArgument::id`               |                                        |
| `AttributeReference`             | `ResourceOrTagArgument::resourceOrTag`       |                                        |
| `EntityReference`                | `ResourceOrTagArgument::resourceOrTag`       |                                        |
| `SummonableEntityReference`      | `ResourceOrTagArgument::resourceOrTag`       |                                        |
| `MobEffectReference`             | `ResourceOrTagArgument::resourceOrTag`       |                                        |
| `EnchantmentReference`           | `ResourceOrTagArgument::resourceOrTag`       |                                        |
| `ConfiguredFeatureReference`     | `ResourceKeyArgument::key`                   |                                        |
| `StructureReference`             | `ResourceKeyArgument::key`                   |                                        |
| `StructureTemplatePoolReference` | `ResourceKeyArgument::key`                   |                                        |
| `BiomeResourceOrTag`             | `ResourceOrTagArgument::resourceOrTag`       |                                        |
| `PoiTypeResourceOrTag`           | `ResourceOrTagArgument::resourceOrTag`       |                                        |
| `StructureResourceOrTagKey`      | `ResourceOrTagKeyArgument::resourceOrTagKey` |                                        |
