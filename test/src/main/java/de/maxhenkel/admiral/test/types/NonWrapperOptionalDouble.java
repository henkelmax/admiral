package de.maxhenkel.admiral.test.types;

import javax.annotation.Nullable;
import java.util.Optional;

public class NonWrapperOptionalDouble {

    private final Optional<Double> value;

    public NonWrapperOptionalDouble(@Nullable Double value) {
        this.value = Optional.ofNullable(value);
    }

    public Optional<Double> getValue() {
        return value;
    }

}
