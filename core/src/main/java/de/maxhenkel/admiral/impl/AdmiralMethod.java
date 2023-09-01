package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.impl.permissions.Permission;
import de.maxhenkel.admiral.impl.permissions.PermissionAnnotationUtil;
import de.maxhenkel.admiral.permissions.PermissionManager;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class AdmiralMethod<S> {

    private boolean registered;
    private final AdmiralClass<S> admiralClass;
    private final Method method;
    private final List<MethodParameter<S, ?, ?>> parameters;
    private List<Command> commands;
    private List<Permission<S>> requiredPermissions;

    public AdmiralMethod(AdmiralClass<S> admiralClass, Method method) {
        this.admiralClass = admiralClass;
        this.method = method;
        this.parameters = new ArrayList<>();
    }

    public void register() {
        if (registered) {
            throw new IllegalStateException("Already registered");
        }
        parameters.addAll(collectParameters());
        commands = Arrays.asList(method.getDeclaredAnnotationsByType(Command.class));
        requiredPermissions = PermissionAnnotationUtil.getPermissions(method);

        for (List<String> path : getPaths()) {
            handleCommand(path);
        }
    }

    private LiteralCommandNode<S> handleCommand(List<String> path) {
        if (path.isEmpty()) {
            throw new IllegalStateException("Can't create command without parameters");
        }

        ArgumentBuilder<S, ?> last = null;
        AdmiralParameter<S, ?, ?> lastParameter = null;
        for (int i = parameters.size() - 1; i >= 0; i--) {
            MethodParameter<S, ?, ?> methodParameter = parameters.get(i);
            if (methodParameter.getAdmiralParameter() == null) {
                continue;
            }
            AdmiralParameter<S, ?, ?> admiralParameter = methodParameter.getAdmiralParameter();
            RequiredArgumentBuilder<S, ?> argument = admiralParameter.toArgument();
            if (last != null) {
                argument.then(last);
            }
            if (lastParameter == null || lastParameter.isOptional()) {
                permission(argument);
                execute(argument);
            }
            last = argument;
            lastParameter = admiralParameter;
        }

        boolean firstPass = true;
        for (int i = path.size() - 1; i >= 0; i--) {
            LiteralArgumentBuilder<S> literal = LiteralArgumentBuilder.literal(path.get(i));
            if (last != null) {
                literal.then(last);
            }
            if (lastParameter == null || lastParameter.isOptional() && firstPass) {
                permission(literal);
                execute(literal);
            }
            last = literal;
            firstPass = false;
        }

        if (last == null) {
            throw new IllegalStateException("Can't create command without parameters");
        }

        registered = true;
        return getDispatcher().register((LiteralArgumentBuilder<S>) last);
    }

    private List<Permission<S>> getRequiredPermissions() {
        List<Permission<S>> permissions = new ArrayList<>();
        permissions.addAll(admiralClass.getRequiredPermissions());
        permissions.addAll(requiredPermissions);
        return permissions;
    }

    private void execute(ArgumentBuilder<S, ?> builder) {
        builder.executes(this::onExecute);
    }

    private void permission(ArgumentBuilder<S, ?> builder) {
        builder.requires(source -> {
            @Nullable PermissionManager<S> permissionManager = getAdmiral().getPermissionManager();
            return getRequiredPermissions().stream().allMatch(p -> p.hasPermission(source, permissionManager));
        });
    }

    private int onExecute(CommandContext<S> context) throws CommandSyntaxException {
        try {
            Object[] args = new Object[parameters.size()];
            for (int i = 0; i < parameters.size(); i++) {
                MethodParameter<S, ?, ?> methodParameter = parameters.get(i);
                Parameter parameter = methodParameter.getParameter();
                AdmiralParameter<S, ?, ?> admiralParameter = methodParameter.getAdmiralParameter();
                if (admiralParameter == null) {
                    if (parameter.getType().isAssignableFrom(CommandContext.class)) {
                        args[i] = context;
                        continue;
                    }
                    throw new IllegalArgumentException(String.format("Invalid parameter type: %s", parameter.getType().getSimpleName()));
                }
                args[i] = admiralParameter.getValue(context);
            }
            Object result = method.invoke(admiralClass.getInstance(), args);
            if (result instanceof Integer) {
                return (int) result;
            }
            // TODO Command result object
        } catch (Exception e) {
            Log.LOGGER.log(Level.SEVERE, "Error while executing command", e);
            throw ExceptionUtils.getAsCommandSyntaxException(e);
        }
        return 0;
    }

    private List<MethodParameter<S, ?, ?>> collectParameters() {
        List<Parameter> list = Arrays.asList(method.getParameters());
        List<MethodParameter<S, ?, ?>> parameters = new ArrayList<>(list.size());
        boolean wasOptional = false;
        for (Parameter parameter : list) {
            if (parameter.getType().isAssignableFrom(CommandContext.class)) {
                parameters.add(new MethodParameter<>(parameter));
                continue;
            }
            AdmiralParameter<S, ?, ?> admiralParameter = new AdmiralParameter<>(this, parameter);
            parameters.add(new MethodParameter<>(parameter, admiralParameter));
            if (!admiralParameter.isOptional() && wasOptional) {
                throw new IllegalStateException("Optional parameters must be at the end of the parameter list");
            }
            wasOptional = admiralParameter.isOptional();
        }

        return parameters;
    }

    private List<List<String>> getPaths() {
        List<List<String>> paths = new ArrayList<>();
        List<List<String>> classPaths = admiralClass.getPaths();
        List<List<String>> methodPaths = commands.stream().map(command -> new ArrayList<>(Arrays.asList(command.value()))).collect(Collectors.toList());
        for (List<String> classPath : classPaths) {
            for (List<String> methodPath : methodPaths) {
                List<String> path = new ArrayList<>(classPath);
                path.addAll(methodPath);
                paths.add(path);
            }
        }
        if (classPaths.isEmpty()) {
            paths.addAll(methodPaths);
        }
        if (methodPaths.isEmpty()) {
            paths.addAll(classPaths);
        }
        return paths;
    }

    public CommandDispatcher<S> getDispatcher() {
        return admiralClass.getAdmiral().getDispatcher();
    }

    public AdmiralClass<S> getAdmiralClass() {
        return admiralClass;
    }

    public AdmiralImpl<S> getAdmiral() {
        return admiralClass.getAdmiral();
    }
}
