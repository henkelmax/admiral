package de.maxhenkel.admiral.argumenttype;

/**
 * A wrapper for a simple string based argument.
 */
public abstract class StringArgumentTypeBase extends SimpleArgumentTypeWrapper<String> implements CharSequence {

    @Override
    public int length() {
        return get().length();
    }

    @Override
    public char charAt(int index) {
        return get().charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return get().subSequence(start, end);
    }

    @Override
    public String toString() {
        return get();
    }

    @Override
    protected Class<String> getArgumentTypeClass() {
        return String.class;
    }
}
