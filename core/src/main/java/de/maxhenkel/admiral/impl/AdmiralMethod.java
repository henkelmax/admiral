package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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

public class AdmiralMethod<S, C> {

    private final AdmiralClass<S, C> admiralClass;
    private final Method method;
    private List<MethodParameter<S, C, ?, ?>> parameters;
    private List<Command> commands;
    private List<Permission<S>> requiredPermissions;

    public AdmiralMethod(AdmiralClass<S, C> admiralClass, Method method) {
        this.admiralClass = admiralClass;
        this.method = method;
        this.parameters = new ArrayList<>();
        this.commands = new ArrayList<>();
        this.requiredPermissions = new ArrayList<>();
    }

    public List<ArgumentBuilder<S, ?>> register() {
        parameters = collectParameters();
        commands = Arrays.asList(method.getDeclaredAnnotationsByType(Command.class));
        requiredPermissions = PermissionAnnotationUtil.getPermissions(method);

        List<ArgumentBuilder<S, ?>> nodes = new ArrayList<>();

        for (List<String> path : getPaths()) {
            nodes.add(handleCommand(path));
        }
        return nodes;
    }

    @Nullable
    private ArgumentBuilder<S, ?> handleCommand(List<String> path) {
        ArgumentBuilder<S, ?> last = null;
        AdmiralParameter<S, C, ?, ?> lastParameter = null;
        for (int i = parameters.size() - 1; i >= 0; i--) {
            MethodParameter<S, C, ?, ?> methodParameter = parameters.get(i);
            if (methodParameter.getAdmiralParameter() == null) {
                continue;
            }
            AdmiralParameter<S, C, ?, ?> admiralParameter = methodParameter.getAdmiralParameter();
            RequiredArgumentBuilder<S, ?> argument = admiralParameter.toArgument();
            if (last != null) {
                argument.then(last);
            }
            permission(null, argument);
            if (lastParameter == null || lastParameter.isOptional()) {
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
            permission(path, literal);
            if ((lastParameter == null || lastParameter.isOptional()) && firstPass) {
                execute(literal);
            }
            last = literal;
            firstPass = false;
        }

        return last;
    }

    protected void execute(ArgumentBuilder<S, ?> builder) {
        builder.executes(this::onExecute);
    }

    private void permission(List<String> path, ArgumentBuilder<S, ?> builder) {
        if (path == null) {
            builder.requires(source -> {
                @Nullable PermissionManager<S> permissionManager = getAdmiral().getPermissionManager();
                if (!admiralClass.checkClassPermissions(source, permissionManager)) {
                    return false;
                }
                return requiredPermissions.stream().allMatch(p -> p.hasPermission(source, permissionManager));
            });
            return;
        }
        List<List<Permission<S>>> perms = getAllRequiredPermissions(path);
        perms.add(requiredPermissions);
        builder.requires(source -> {
            @Nullable PermissionManager<S> permissionManager = getAdmiral().getPermissionManager();
            if (!admiralClass.checkClassPermissions(source, permissionManager)) {
                return false;
            }
            return perms.stream().anyMatch(permissions -> permissions.stream().allMatch(p -> p.hasPermission(source, permissionManager)));
        });
    }

    private List<List<Permission<S>>> getAllRequiredPermissions(List<String> path) {
        return admiralClass.getPermissions().computeIfAbsent(String.join("/", path), k -> new ArrayList<>());
    }

    private int onExecute(CommandContext<S> context) throws CommandSyntaxException {
        try {
            Object[] args = new Object[parameters.size()];
            for (int i = 0; i < parameters.size(); i++) {
                MethodParameter<S, C, ?, ?> methodParameter = parameters.get(i);
                Parameter parameter = methodParameter.getParameter();
                AdmiralParameter<S, C, ?, ?> admiralParameter = methodParameter.getAdmiralParameter();
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
            return 1;
        } catch (Exception e) {
            Log.LOGGER.log(Level.SEVERE, "Error while executing command", e);
            throw ExceptionUtils.getAsCommandSyntaxException(e);
        }
    }

    private List<MethodParameter<S, C, ?, ?>> collectParameters() {
        List<Parameter> list = Arrays.asList(method.getParameters());
        List<MethodParameter<S, C, ?, ?>> parameters = new ArrayList<>(list.size());
        boolean wasOptional = false;
        for (Parameter parameter : list) {
            if (parameter.getType().isAssignableFrom(CommandContext.class)) {
                parameters.add(new MethodParameter<>(parameter));
                continue;
            }
            AdmiralParameter<S, C, ?, ?> admiralParameter = new AdmiralParameter<>(this, parameter);
            parameters.add(new MethodParameter<>(parameter, admiralParameter));
            if (!admiralParameter.isOptional() && wasOptional) {
                throw new IllegalStateException("Optional parameters must be at the end of the parameter list");
            }
            wasOptional = admiralParameter.isOptional();
        }

        return parameters;
    }

    private List<List<String>> getPaths() {
        return commands.stream().map(command -> new ArrayList<>(Arrays.asList(command.value()))).collect(Collectors.toList());
    }

    public AdmiralClass<S, C> getAdmiralClass() {
        return admiralClass;
    }

    public AdmiralImpl<S, C> getAdmiral() {
        return admiralClass.getAdmiral();
    }
}
