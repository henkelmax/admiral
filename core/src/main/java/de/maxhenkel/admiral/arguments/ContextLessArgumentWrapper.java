package de.maxhenkel.admiral.arguments;

public abstract class ContextLessArgumentWrapper<A, T> extends ArgumentWrapper<Object, A, T> {

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

    protected T convert(A value) {
        return null;
    }

}
