package de.maxhenkel.admiral.argumenttype;

import javax.annotation.Nullable;

public interface ArgumentTypeRegistry {

    /**
     * Registers an argument type
     *
     * @param customTypeClass       the class of your custom type
     * @param argumentType          the argument type. Use {@link RangedArgumentTypeSupplier} if the argument type is ranged
     * @param argumentTypeConverter the argument type converter. Can be <code>null</code> if the argument type is not converted
     * @param <S>                   the command source type
     * @param <A>                   the argument type of the brigadier argument
     * @param <T>                   the converted argument type
     */
    <S, C, A, T> void register(Class<T> customTypeClass, ArgumentTypeSupplier<C, A> argumentType, @Nullable ArgumentTypeConverter<S, A, T> argumentTypeConverter);

    /**
     * Registers an argument type
     *
     * @param argumentTypeClass the class of the argument type
     * @param argumentType      the argument type. Use {@link RangedArgumentTypeSupplier} if the argument type is ranged
     * @param <A>               the argument type of the brigadier argument
     */
    <C, A> void register(Class<A> argumentTypeClass, ArgumentTypeSupplier<C, A> argumentType);

}
