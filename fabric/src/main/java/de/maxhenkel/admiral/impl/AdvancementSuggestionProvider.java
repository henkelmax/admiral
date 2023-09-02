package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.AdvancementCommands;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class AdvancementSuggestionProvider {

    public static SuggestionProvider<CommandSourceStack> getAdvancementSuggestions() {
        Field[] declaredFields = AdvancementCommands.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() != SuggestionProvider.class) {
                continue;
            }
            field.setAccessible(true);
            if (!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            try {
                return (SuggestionProvider<CommandSourceStack>) field.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalStateException("Could not find advancement suggestions");
    }


}
