package de.maxhenkel.admiral.impl.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import de.maxhenkel.admiral.impl.AdmiralParameter;

import java.util.function.Supplier;

public class ArgumentWrapper<A, T> extends ArgumentBase<A, T> {

    protected Supplier<ArgumentType<A>> argumentType;

    public ArgumentWrapper(Class<A> argumentTypeClass, Class<T> parameterClass, Supplier<ArgumentType<A>> argumentType) {
        super(argumentTypeClass, parameterClass);
        this.argumentType = argumentType;
    }

    @Override
    public Supplier<ArgumentType<A>> getArgumentType(AdmiralParameter<?, A, T> parameter) {
        return argumentType;
    }
}
