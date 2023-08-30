package de.maxhenkel.admiral.arguments;

public abstract class SimpleArgumentWrapper<A> extends ArgumentWrapper<Object, A, A> {

    public SimpleArgumentWrapper(A value) {
        super(null, value);
    }

    @Override
    public A get() {
        return value;
    }

    protected A convert(A value) {
        return null;
    }

}
