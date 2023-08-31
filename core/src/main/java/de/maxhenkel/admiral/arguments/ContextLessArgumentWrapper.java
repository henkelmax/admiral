package de.maxhenkel.admiral.arguments;

import javax.annotation.Nullable;

/**
 * @param <A> the argument type of the brigadier argument
 * @param <T> the converted argument type
 */
public abstract class ContextLessArgumentWrapper<A, T> extends ArgumentWrapper<Object, A, T> {

    /**
     * This constructor gets invoked automatically.
     * Don't call it manually or add any parameters.
     *
     * @param value The argument value
     */
    public ContextLessArgumentWrapper(A value) {
        super(null, value);
    }

    @Override
    public T get() {
        T convert = convert(value);
        if (convert == null) {
            return (T) value;
        }
        return convert;
    }

    /**
     * @param value the argument value
     * @return the converted argument value or null if {@link #value} is the same as the converted value
     */
    @Override
    @Nullable
    protected T convert(A value) {
        return null;
    }

}
