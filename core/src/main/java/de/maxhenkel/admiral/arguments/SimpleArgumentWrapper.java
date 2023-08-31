package de.maxhenkel.admiral.arguments;

/**
 * A wrapper for a simple argument.
 *
 * @param <A> the argument type of the brigadier argument
 */
public abstract class SimpleArgumentWrapper<A> extends ArgumentWrapper<Object, A, A> {

    /**
     * This constructor gets invoked automatically.
     * Don't call it manually or add any parameters.
     *
     * @param value The argument value
     */
    public SimpleArgumentWrapper(A value) {
        super(null, value);
    }

    @Override
    public A get() {
        return value;
    }

    @Override
    protected A convert(A value) {
        return null;
    }

}
