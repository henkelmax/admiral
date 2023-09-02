package de.maxhenkel.admiral.impl.arguments;

public class SimpleArgumentType<T> {

    protected final T value;

    public SimpleArgumentType(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
