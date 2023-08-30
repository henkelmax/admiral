package de.maxhenkel.admiral.impl;

import de.maxhenkel.admiral.annotations.Max;
import de.maxhenkel.admiral.annotations.Min;
import de.maxhenkel.admiral.annotations.MinMax;

import javax.annotation.Nullable;

public class AnnotationUtil {

    @Nullable
    public static <A> A getMin(AdmiralParameter<?, A, ?> parameter) {
        String value = null;
        MinMax minMax = parameter.getParameter().getDeclaredAnnotation(MinMax.class);
        Min min = parameter.getParameter().getDeclaredAnnotation(Min.class);
        if (minMax != null) {
            value = minMax.min();
        } else if (min != null) {
            value = min.value();
        }
        if (value == null) {
            return null;
        }
        return parseString(value, parameter.getArgumentType());
    }

    @Nullable
    public static <A> A getMax(AdmiralParameter<?, A, ?> parameter) {
        String value = null;
        MinMax minMax = parameter.getParameter().getDeclaredAnnotation(MinMax.class);
        Max max = parameter.getParameter().getDeclaredAnnotation(Max.class);
        if (minMax != null) {
            value = minMax.max();
        } else if (max != null) {
            value = max.value();
        }
        if (value == null) {
            return null;
        }
        return parseString(value, parameter.getArgumentType());
    }

    public static <A> A parseString(String value, Class<A> clazz) {
        if (Byte.class.isAssignableFrom(clazz)) {
            return (A) (Byte) Byte.parseByte(value);
        } else if (byte.class.isAssignableFrom(clazz)) {
            return (A) (Byte) Byte.parseByte(value);
        } else if (Short.class.isAssignableFrom(clazz)) {
            return (A) (Short) Short.parseShort(value);
        } else if (short.class.isAssignableFrom(clazz)) {
            return (A) (Short) Short.parseShort(value);
        } else if (Integer.class.isAssignableFrom(clazz)) {
            return (A) (Integer) Integer.parseInt(value);
        } else if (int.class.isAssignableFrom(clazz)) {
            return (A) (Integer) Integer.parseInt(value);
        } else if (Long.class.isAssignableFrom(clazz)) {
            return (A) (Long) Long.parseLong(value);
        } else if (long.class.isAssignableFrom(clazz)) {
            return (A) (Long) Long.parseLong(value);
        } else if (Float.class.isAssignableFrom(clazz)) {
            return (A) (Float) Float.parseFloat(value);
        } else if (float.class.isAssignableFrom(clazz)) {
            return (A) (Float) Float.parseFloat(value);
        } else if (Double.class.isAssignableFrom(clazz)) {
            return (A) (Double) Double.parseDouble(value);
        } else if (double.class.isAssignableFrom(clazz)) {
            return (A) (Double) Double.parseDouble(value);
        }
        return null;
    }

}
