package de.maxhenkel.admiral.impl;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import de.maxhenkel.admiral.annotations.Command;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdmiralMethod<S> {

    private boolean registered;
    private final AdmiralClass<S> admiralClass;
    private final Method method;
    private final List<MethodParameter<S, ?, ?>> parameters;
    private Command[] methodAnnotations;

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
        methodAnnotations = method.getDeclaredAnnotationsByType(Command.class);

        for (List<String> path : getPaths()) {
            handleCommand(path);
        }
    }

    private LiteralCommandNode<S> handleCommand(List<String> path) {
        if (path.isEmpty()) {
            throw new IllegalStateException("Can't create command without arguments");
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
                execute(argument);
            }
            last = argument;
            lastParameter = admiralParameter;
        }

        for (int i = path.size() - 1; i >= 0; i--) {
            LiteralArgumentBuilder<S> literal = LiteralArgumentBuilder.literal(path.get(i));
            if (last != null) {
                literal.then(last);
            }
            if (lastParameter == null || lastParameter.isOptional()) {
                execute(literal);
            }
            last = literal;
        }

        registered = true;
        return getDispatcher().register((LiteralArgumentBuilder<S>) last);
    }

    private void execute(ArgumentBuilder<S, ?> builder) {
        builder.executes(this::onExecute);
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
            if (e instanceof CommandSyntaxException) {
                throw (CommandSyntaxException) e;
            } else if (e.getCause() instanceof CommandSyntaxException) {
                throw (CommandSyntaxException) e.getCause();
            }
            // TODO remove
            e.printStackTrace();
            throw new RuntimeException(e);
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
        List<List<String>> methodPaths = Arrays.stream(methodAnnotations).map(command -> new ArrayList<>(Arrays.asList(command.value()))).collect(Collectors.toList());
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
        return admiralClass.getDispatcher();
    }

    public AdmiralClass<S> getAdmiralClass() {
        return admiralClass;
    }
}
