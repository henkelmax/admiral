package de.maxhenkel.admiral.impl.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import de.maxhenkel.admiral.impl.AdmiralParameter;
import de.maxhenkel.admiral.impl.AnnotationUtil;

import java.util.function.Supplier;

public class MinMaxArgumentWrapper<A, T> extends ArgumentBase<A, T> {

    private final MinMaxArgumentTypeSupplier<A> minMax;
    private final MinArgumentTypeSupplier<A> min;
    private final MaxArgumentTypeSupplier<A> max;
    private final ArgumentTypeSupplier<A> arg;

    public MinMaxArgumentWrapper(Class<A> argumentTypeClass, Class<T> parameterClass, MinMaxArgumentTypeSupplier<A> minMax, MinArgumentTypeSupplier<A> min, MaxArgumentTypeSupplier<A> max, ArgumentTypeSupplier<A> arg) {
        super(argumentTypeClass, parameterClass);
        this.minMax = minMax;
        this.min = min;
        this.max = max;
        this.arg = arg;
    }

    @Override
    public Supplier<ArgumentType<A>> getArgumentType(AdmiralParameter<?, A, T> parameter) {
        A minVal = AnnotationUtil.getMin(parameter);
        A maxVal = AnnotationUtil.getMax(parameter);
        if (minVal != null && maxVal != null) {
            return () -> minMax.get(minVal, maxVal);
        }
        if (minVal != null) {
            return () -> min.get(minVal);
        }
        if (maxVal != null) {
            return () -> max.get(maxVal);
        }
        return () -> arg.get();
    }

    public MinMaxArgumentTypeSupplier<A> getMinMax() {
        return minMax;
    }
}
