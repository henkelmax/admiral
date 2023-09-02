package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.LocateCommand;

public class DynamicExceptionTypes {

    /**
     * From {@link LocateCommand#ERROR_STRUCTURE_INVALID}
     */
    public static final DynamicCommandExceptionType ERROR_STRUCTURE_INVALID = new DynamicCommandExceptionType(object -> Component.translatable("commands.locate.structure.invalid", object));

}
