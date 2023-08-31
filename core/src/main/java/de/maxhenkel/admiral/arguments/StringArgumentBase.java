package de.maxhenkel.admiral.arguments;

/**
 * A wrapper for a simple string based argument.
 */
public class StringArgumentBase extends SimpleArgumentWrapper<String> implements CharSequence {

    public StringArgumentBase(String string) {
        super(string);
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
