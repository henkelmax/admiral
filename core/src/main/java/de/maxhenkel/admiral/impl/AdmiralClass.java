package de.maxhenkel.admiral.impl;

import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.impl.permissions.Permission;
import de.maxhenkel.admiral.impl.permissions.PermissionAnnotationUtil;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdmiralClass<S, C> {

    private boolean registered;
    private AdmiralImpl<S, C> admiral;
    private final Class<?> clazz;
    private Object instance;
    private List<Command> commands;
    private List<Permission<S>> requiredPermissions;

    public AdmiralClass(AdmiralImpl<S, C> admiral, Class<?> clazz) {
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
        requiredPermissions = PermissionAnnotationUtil.getPermissions(clazz);

        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method method : declaredMethods) {
            method.setAccessible(true);
            if (method.getDeclaredAnnotationsByType(Command.class).length == 0) {
                continue;
            }
            AdmiralMethod<S, C> admiralMethod = new AdmiralMethod<>(this, method);
            admiralMethod.register();
        }
        registered = true;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public AdmiralImpl<S, C> getAdmiral() {
        return admiral;
    }

    public List<Permission<S>> getRequiredPermissions() {
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
