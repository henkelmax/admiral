package de.maxhenkel.admiral.impl.arguments;

/**
 * A wrapper for a simple string based argument.
 */
public abstract class StringArgumentTypeBase implements CharSequence {

    protected String value;

    public StringArgumentTypeBase(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public char charAt(int index) {
        return value.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return value.subSequence(start, end);
    }

    @Override
    public String toString() {
        return value;
    }

}
