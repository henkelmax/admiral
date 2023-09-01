package de.maxhenkel.admiral.test.types;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import de.maxhenkel.admiral.argumenttype.SimpleArgumentTypeWrapper;
import org.jetbrains.annotations.Nullable;

public class WrapperDouble extends SimpleArgumentTypeWrapper<Double> {

    @Override
    protected ArgumentType<Double> getArgumentType() {
        return DoubleArgumentType.doubleArg();
    }

    @Override
    protected ArgumentType<Double> getArgumentType(@Nullable Double min, @Nullable Double max) {
        return DoubleArgumentType.doubleArg(min == null ? Double.MIN_VALUE : min, max == null ? Double.MAX_VALUE : max);
    }

}
