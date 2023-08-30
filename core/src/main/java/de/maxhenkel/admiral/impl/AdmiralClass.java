package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.CommandDispatcher;
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
    @Nullable
    private Object instance;
    private Command[] classAnnotations;

    public AdmiralClass(ArgumentRegistryImpl argumentRegistry, CommandDispatcher<S> dispatcher, Class<?> clazz) {
        this.argumentRegistry = argumentRegistry;
        this.dispatcher = dispatcher;
        this.clazz = clazz;
    }

    public void register() {
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
        classAnnotations = clazz.getDeclaredAnnotationsByType(Command.class);

        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method method : declaredMethods) {
            AdmiralMethod<S> admiralMethod = new AdmiralMethod<>(this, method);
            admiralMethod.register();
        }
        registered = true;
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

    public List<List<String>> getPaths() {
        List<List<String>> paths = new ArrayList<>();
        for (Command command : classAnnotations) {
            paths.add(new ArrayList<>(Arrays.asList(command.value())));
        }
        return paths;
    }

}
