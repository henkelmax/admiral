package de.maxhenkel.admiral.argumenttype;

import javax.annotation.Nullable;

public interface ArgumentTypeRegistry {

    /**
     * Registers an argument type
     *
     * @param argumentType          the argument type. Use {@link RangedArgumentTypeSupplier} if the argument type is ranged
     * @param argumentTypeConverter the argument type converter. Can be <code>null</code> if the argument type is not converted
     * @param argumentTypeClass     the class of the argument type
     * @param <S>                   the command source type
     * @param <A>                   the argument type of the brigadier argument
     * @param <T>                   the converted argument type
     */
    <S, A, T> void register(ArgumentTypeSupplier<A> argumentType, @Nullable ArgumentTypeConverter<S, A, T> argumentTypeConverter, Class<T> argumentTypeClass);

    /**
     * Registers an argument type
     *
     * @param argumentType      the argument type. Use {@link RangedArgumentTypeSupplier} if the argument type is ranged
     * @param argumentTypeClass the class of the argument type
     * @param <A>               the argument type of the brigadier argument
     */
    <A> void register(ArgumentTypeSupplier<A> argumentType, Class<A> argumentTypeClass);

}
