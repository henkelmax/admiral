package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.AdvancementCommands;
import net.minecraft.server.commands.ExecuteCommand;
import net.minecraft.server.commands.ItemCommands;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionSuggestionProviders {

    public static SuggestionProvider<CommandSourceStack> getAdvancementSuggestions() {
        return getFromClass(AdvancementCommands.class);
    }

    public static SuggestionProvider<CommandSourceStack> getPredicateSuggestions() {
        return getFromClass(ExecuteCommand.class);
    }

    public static SuggestionProvider<CommandSourceStack> getLootItemSuggestions() {
        return getFromClass(ItemCommands.class);
    }

    private static SuggestionProvider<CommandSourceStack> getFromClass(Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();

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
        throw new IllegalStateException(String.format("Could not find suggestion provider in class %s", clazz.getSimpleName()));
    }


}
