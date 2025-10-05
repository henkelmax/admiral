package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.impl.permissions.Permission;
import de.maxhenkel.admiral.impl.permissions.PermissionAnnotationUtil;
import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AdmiralClass<S, C> {

    private boolean registered;
    private AdmiralImpl<S, C> admiral;
    private final Class<?> clazz;
    private Object instance;
    private List<Command> commands;
    private List<Permission<S>> requiredPermissions;
    private Map<String, List<List<Permission<S>>>> permissions;

    public AdmiralClass(AdmiralImpl<S, C> admiral, Class<?> clazz) {
        this.admiral = admiral;
        this.clazz = clazz;
        this.permissions = new LinkedHashMap<>();
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

        HashSet<Method> allMethods = new HashSet<>(Arrays.asList(clazz.getDeclaredMethods()));
        allMethods.addAll(Arrays.asList(clazz.getMethods()));

        int registeredMethods = 0;
        for (Method method : allMethods) {
            method.setAccessible(true);
            if (method.getDeclaredAnnotationsByType(Command.class).length == 0) {
                continue;
            }
            registeredMethods++;

            AdmiralMethod<S, C> admiralMethod = new AdmiralMethod<>(this, method);
            List<AdmiralMethod.WrappedArgumentBuilder<S>> nodes = admiralMethod.register();

            List<List<String>> classPaths = getPaths();

            if (classPaths.isEmpty()) {
                classPaths.add(new ArrayList<>());
            }

            for (List<String> path : classPaths) {
                if (path.isEmpty()) {
                    for (AdmiralMethod.WrappedArgumentBuilder<S> node : nodes) {
                        if (node.builder instanceof LiteralArgumentBuilder) {
                            admiral.getDispatcher().register((LiteralArgumentBuilder) node.builder);
                        }
                    }
                    continue;
                }

                LiteralArgumentBuilder<S> last = null;
                for (int i = path.size() - 1; i >= 0; i--) {
                    LiteralArgumentBuilder<S> literal = LiteralArgumentBuilder.literal(path.get(i));
                    if (last != null) {
                        literal.then(last);
                    } else {
                        for (AdmiralMethod.WrappedArgumentBuilder<S> node : nodes) {
                            if (node.builder == null) {
                                admiralMethod.execute(literal);
                                continue;
                            } else if (node.needsExecution) {
                                admiralMethod.execute(literal);
                            }
                            literal.then(node.builder);
                        }
                    }

                    last = literal;
                }
                admiral.getDispatcher().register(last);
            }
        }

        if (commands.isEmpty() && registeredMethods == 0) {
            throw new IllegalStateException(String.format("Class %s does not contain any commands", clazz.getSimpleName()));
        }

        registered = true;
    }

    public AdmiralImpl<S, C> getAdmiral() {
        return admiral;
    }

    public Map<String, List<List<Permission<S>>>> getPermissions() {
        return permissions;
    }

    public boolean checkClassPermissions(S source, @Nullable PermissionManager<S> permissionManager) {
        return requiredPermissions.stream().allMatch(permission -> permission.hasPermission(source, permissionManager));
    }

    @Nullable
    public Object getInstance() {
        return instance;
    }

    public List<List<String>> getPaths() {
        return commands.stream().map(command -> Arrays.asList(command.value())).collect(Collectors.toList());
    }

}
