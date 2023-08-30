package de.maxhenkel.admiral.impl.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import de.maxhenkel.admiral.impl.AdmiralParameter;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.function.Supplier;

public abstract class ArgumentBase<A, T> {

    protected final Class<A> argumentTypeClass;
    protected final Class<T> parameterClass;
    /**
     * The constructor of the wrapper type that takes the context (Optional) and the argument type as parameter
     */
    @Nullable
    private Constructor<T> wrapperConstructor;

    public ArgumentBase(Class<A> argumentTypeClass, Class<T> parameterClass) {
        this.argumentTypeClass = argumentTypeClass;
        this.parameterClass = parameterClass;
        if (!parameterClass.equals(argumentTypeClass)) {
            try {
                try {
                    wrapperConstructor = parameterClass.getDeclaredConstructor(CommandContext.class, argumentTypeClass);
                } catch (NoSuchMethodException e) {
                    wrapperConstructor = parameterClass.getDeclaredConstructor(argumentTypeClass);
                }
                wrapperConstructor.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } else {
            wrapperConstructor = null;
        }
    }

    public Class<T> getParameterClass() {
        return parameterClass;
    }

    public Class<A> getArgumentTypeClass() {
        return argumentTypeClass;
    }

    public abstract Supplier<ArgumentType<A>> getArgumentType(AdmiralParameter<?, A, T> parameter);

    public T convert(CommandContext<?> context, A value) {
        if (wrapperConstructor == null) {
            if (value == null && parameterClass.isPrimitive()) {
                return (T) defaultValue(parameterClass);
            }
            return (T) value;
        }
        try {
            Object[] params = new Object[wrapperConstructor.getParameterCount()];
            if (params.length == 1) {
                params[0] = value;
            } else if (params.length == 2) {
                params[0] = context;
                params[1] = value;
            } else {
                throw new RuntimeException(String.format("Invalid constructor for wrapper %s", wrapperConstructor.getDeclaringClass().getSimpleName()));
            }
            return wrapperConstructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object defaultValue(Class<?> type) {
        if (type == byte.class) return (byte) 0;
        if (type == short.class) return (short) 0;
        if (type == int.class) return 0;
        if (type == long.class) return 0L;
        if (type == float.class) return 0.0F;
        if (type == double.class) return 0.0D;
        if (type == char.class) return '\u0000';
        if (type == boolean.class) return false;
        throw new IllegalStateException(String.format("Could not find default value for %s", type.getSimpleName()));
    }

}
