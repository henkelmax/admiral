package de.maxhenkel.admiral.impl;

import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.RequiresPermission;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdmiralClass<S> {

    private boolean registered;
    private AdmiralImpl<S> admiral;
    private final Class<?> clazz;
    @Nullable
    private Object instance;
    private List<Command> commands;
    private List<RequiresPermission> requiredPermissions;

    public AdmiralClass(AdmiralImpl<S> admiral, Class<?> clazz) {
        this.admiral = admiral;
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
        commands = Arrays.asList(clazz.getDeclaredAnnotationsByType(Command.class));
        requiredPermissions = Arrays.asList(clazz.getDeclaredAnnotationsByType(RequiresPermission.class));

        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method method : declaredMethods) {
            method.setAccessible(true);
            AdmiralMethod<S> admiralMethod = new AdmiralMethod<>(this, method);
            admiralMethod.register();
        }
        registered = true;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public AdmiralImpl<S> getAdmiral() {
        return admiral;
    }

    public List<RequiresPermission> getRequiredPermissions() {
        return requiredPermissions;
    }

    @Nullable
    public Object getInstance() {
        return instance;
    }

    public List<List<String>> getPaths() {
        List<List<String>> paths = new ArrayList<>();
        for (Command command : commands) {
            paths.add(new ArrayList<>(Arrays.asList(command.value())));
        }
        return paths;
    }

}
