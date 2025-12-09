package de.maxhenkel.admiral.test.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.arguments.GameTestInstances;
import de.maxhenkel.admiral.arguments.HexColor;
import de.maxhenkel.admiral.test.AdmiralMod;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.world.inventory.SlotRange;
import net.minecraft.world.waypoints.WaypointTransmitter;

@Command("other")
public class OtherCommands {

    @Command("waypointtransmitter")
    public void waypointtransmitter(CommandContext<CommandSourceStack> context, WaypointTransmitter waypointTransmitter) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("waypointTransmitter: {}", waypointTransmitter);
    }

    @Command("hexcolor")
    public void hexcolor(CommandContext<CommandSourceStack> context, HexColor hexColor) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("hexColor: {}", hexColor.get());
    }

    @Command("intrange")
    public void intrange(CommandContext<CommandSourceStack> context, MinMaxBounds.Ints intRange) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("intRange - min: {}, max: {}", intRange.min(), intRange.max());
    }

    @Command("doublerange")
    public void doublerange(CommandContext<CommandSourceStack> context, MinMaxBounds.Doubles doubleRange) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("doubleRange - min: {}, max: {}", doubleRange.min(), doubleRange.max());
    }

    @Command("slotrange")
    public void slotrange(CommandContext<CommandSourceStack> context, SlotRange slotRange) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("slotRange: {}", slotRange);
    }

    @Command("dialog")
    public void dialog(CommandContext<CommandSourceStack> context, Dialog dialog) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("dialog: {}", dialog);
    }

    @Command("gametestinstances")
    public void gametestinstances(CommandContext<CommandSourceStack> context, GameTestInstances gameTestInstances) throws CommandSyntaxException {
        AdmiralMod.LOGGER.info("gameTestInstances: {}", gameTestInstances);
    }

}
