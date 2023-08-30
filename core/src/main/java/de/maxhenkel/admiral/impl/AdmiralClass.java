package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import de.maxhenkel.admiral.annotations.Command;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdmiralClass<S> {

    private boolean registered;
    private final ArgumentRegistryImpl argumentRegistry;
    private final CommandDispatcher<S> dispatcher;
    private final Class<?> clazz;
    private final List<AdmiralMethod<S>> methods;
    private final List<LiteralCommandNode<S>> commands;
    @Nullable
    private Object instance;
    @Nullable
    private Command classAnnotation;

    public AdmiralClass(ArgumentRegistryImpl argumentRegistry, CommandDispatcher<S> dispatcher, Class<?> clazz) {
        this.argumentRegistry = argumentRegistry;
        this.dispatcher = dispatcher;
        this.clazz = clazz;
        this.methods = new ArrayList<>();
        this.commands = new ArrayList<>();
    }

    public List<LiteralCommandNode<S>> register() {
        if (registered) {
            throw new IllegalStateException("Already registered");
        }
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(String.format("Class %s does not have a no-arguments constructor", clazz.getSimpleName()), e);
        }
        classAnnotation = clazz.getDeclaredAnnotation(Command.class);

        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method method : declaredMethods) {
            AdmiralMethod<S> admiralMethod = new AdmiralMethod<>(this, method);
            LiteralCommandNode<S> register = admiralMethod.register();
            if (register != null) {
                methods.add(admiralMethod);
                commands.add(register);
            }
            // Ignore methods without @Command annotation
        }
        registered = true;
        return commands;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public ArgumentRegistryImpl getArgumentRegistry() {
        return argumentRegistry;
    }

    public CommandDispatcher<S> getDispatcher() {
        return dispatcher;
    }

    @Nullable
    public Object getInstance() {
        return instance;
    }

    @Nullable
    public Command getClassAnnotation() {
        return classAnnotation;
    }

    public List<String> getPath() {
        if (classAnnotation == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(classAnnotation.value()));
    }

}
