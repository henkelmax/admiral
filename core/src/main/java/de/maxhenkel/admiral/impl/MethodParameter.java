package de.maxhenkel.admiral.impl;

import javax.annotation.Nullable;
import java.lang.reflect.Parameter;

public class MethodParameter<S, C, A, T> {

    private final Parameter parameter;
    @Nullable
    private final AdmiralParameter<S, C, A, T> admiralParameter;


    public MethodParameter(Parameter parameter, @Nullable AdmiralParameter<S, C, A, T> admiralParameter) {
        this.parameter = parameter;
        this.admiralParameter = admiralParameter;
    }

    public MethodParameter(AdmiralMethod<S, C> method, Parameter parameter) {
        this(parameter, new AdmiralParameter<>(method, parameter));
    }

    public MethodParameter(Parameter parameter) {
        this(parameter, null);
    }

    public Parameter getParameter() {
        return parameter;
    }

    @Nullable
    public AdmiralParameter<S, C, A, T> getAdmiralParameter() {
        return admiralParameter;
    }
}
