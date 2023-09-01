package de.maxhenkel.admiral.argumenttypes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.maxhenkel.admiral.TestUtils;
import de.maxhenkel.admiral.annotations.Command;
import de.maxhenkel.admiral.annotations.OptionalArgument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.stubbing.OngoingStubbing;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PrimitivesTest {

    @Test
    @DisplayName("Boolean argument type")
    public void booleanArgumentType() throws CommandSyntaxException {
        testType("boolean true", BooleanCommands.class, c -> when(BooleanCommands.booleanCommand(c.capture())), boolean.class, true);
        testType("optional_boolean true", BooleanCommands.class, c -> when(BooleanCommands.optionalBooleanCommand(c.capture())), boolean.class, true);
        testType("optional_boolean", BooleanCommands.class, c -> when(BooleanCommands.optionalBooleanCommand(c.capture())), boolean.class, false);
        testType("boolean_object true", BooleanCommands.class, c -> when(BooleanCommands.booleanObjectCommand(c.capture())), Boolean.class, true);
        testType("optional_boolean_object true", BooleanCommands.class, c -> when(BooleanCommands.optionalBooleanObjectCommand(c.capture())), Boolean.class, true);
        testType("optional_boolean_object", BooleanCommands.class, c -> when(BooleanCommands.optionalBooleanObjectCommand(c.capture())), Boolean.class, null);
    }

    @Test
    @DisplayName("Long argument type")
    public void longArgumentType() throws CommandSyntaxException {
        testType("long 123", LongCommands.class, c -> when(LongCommands.longCommand(c.capture())), long.class, 123L);
        testType("optional_long 123", LongCommands.class, c -> when(LongCommands.optionalLongCommand(c.capture())), long.class, 123L);
        testType("optional_long", LongCommands.class, c -> when(LongCommands.optionalLongCommand(c.capture())), long.class, 0L);
        testType("long_object 123", LongCommands.class, c -> when(LongCommands.longObjectCommand(c.capture())), Long.class, 123L);
        testType("optional_long_object 123", LongCommands.class, c -> when(LongCommands.optionalLongObjectCommand(c.capture())), Long.class, 123L);
        testType("optional_long_object", LongCommands.class, c -> when(LongCommands.optionalLongObjectCommand(c.capture())), Long.class, null);
    }

    @Test
    @DisplayName("Integer argument type")
    public void integerArgumentType() throws CommandSyntaxException {
        testType("int 123", IntegerCommands.class, c -> when(IntegerCommands.intCommand(c.capture())), int.class, 123);
        testType("optional_int 123", IntegerCommands.class, c -> when(IntegerCommands.optionalIntCommand(c.capture())), int.class, 123);
        testType("optional_int", IntegerCommands.class, c -> when(IntegerCommands.optionalIntCommand(c.capture())), int.class, 0);
        testType("integer 123", IntegerCommands.class, c -> when(IntegerCommands.integerCommand(c.capture())), Integer.class, 123);
        testType("optional_integer 123", IntegerCommands.class, c -> when(IntegerCommands.optionalIntegerCommand(c.capture())), Integer.class, 123);
        testType("optional_integer", IntegerCommands.class, c -> when(IntegerCommands.optionalIntegerCommand(c.capture())), Integer.class, null);
    }

    @Test
    @DisplayName("Float argument type")
    public void floatArgumentType() throws CommandSyntaxException {
        testType("float 123.45", FloatCommands.class, c -> when(FloatCommands.floatCommand(c.capture())), float.class, 123.45f);
        testType("optional_float 123.45", FloatCommands.class, c -> when(FloatCommands.optionalFloatCommand(c.capture())), float.class, 123.45f);
        testType("optional_float", FloatCommands.class, c -> when(FloatCommands.optionalFloatCommand(c.capture())), float.class, 0.0f);
        testType("float_object 123.45", FloatCommands.class, c -> when(FloatCommands.floatObjectCommand(c.capture())), Float.class, 123.45f);
        testType("optional_float_object 123.45", FloatCommands.class, c -> when(FloatCommands.optionalFloatObjectCommand(c.capture())), Float.class, 123.45f);
        testType("optional_float_object", FloatCommands.class, c -> when(FloatCommands.optionalFloatObjectCommand(c.capture())), Float.class, null);
    }

    @Test
    @DisplayName("Double argument type")
    public void doubleArgumentType() throws CommandSyntaxException {
        testType("double 123.45", DoubleCommands.class, c -> when(DoubleCommands.doubleCommand(c.capture())), double.class, 123.45);
        testType("optional_double 123.45", DoubleCommands.class, c -> when(DoubleCommands.optionalDoubleCommand(c.capture())), double.class, 123.45);
        testType("optional_double", DoubleCommands.class, c -> when(DoubleCommands.optionalDoubleCommand(c.capture())), double.class, 0.0);
        testType("double_object 123.45", DoubleCommands.class, c -> when(DoubleCommands.doubleObjectCommand(c.capture())), Double.class, 123.45);
        testType("optional_double_object 123.45", DoubleCommands.class, c -> when(DoubleCommands.optionalDoubleObjectCommand(c.capture())), Double.class, 123.45);
        testType("optional_double_object", DoubleCommands.class, c -> when(DoubleCommands.optionalDoubleObjectCommand(c.capture())), Double.class, null);
    }

    private static <C, V> void testType(String command, Class<C> commandClass, Function<ArgumentCaptor<V>, OngoingStubbing<Integer>> capturedWhenMethod, Class<V> valueClass, V expected) throws CommandSyntaxException {
        try (MockedStatic<C> staticMock = mockStatic(commandClass)) {
            ArgumentCaptor<V> captor = ArgumentCaptor.forClass(valueClass);
            capturedWhenMethod.apply(captor).thenReturn(1);
            TestUtils.executeCommand(command, commandClass);
            assertEquals(expected, captor.getValue());
        }
    }

    private static class BooleanCommands {
        @Command("boolean")
        public static int booleanCommand(boolean value) {
            return 1;
        }

        @Command("optional_boolean")
        public static int optionalBooleanCommand(@OptionalArgument boolean value) {
            return 1;
        }

        @Command("boolean_object")
        public static int booleanObjectCommand(Boolean value) {
            return 1;
        }

        @Command("optional_boolean_object")
        public static int optionalBooleanObjectCommand(@OptionalArgument Boolean value) {
            return 1;
        }
    }

    private static class LongCommands {
        @Command("long")
        public static int longCommand(long value) {
            return 1;
        }

        @Command("optional_long")
        public static int optionalLongCommand(@OptionalArgument long value) {
            return 1;
        }

        @Command("long_object")
        public static int longObjectCommand(Long value) {
            return 1;
        }

        @Command("optional_long_object")
        public static int optionalLongObjectCommand(@OptionalArgument Long value) {
            return 1;
        }
    }

    private static class IntegerCommands {
        @Command("int")
        public static int intCommand(int value) {
            return 1;
        }

        @Command("optional_int")
        public static int optionalIntCommand(@OptionalArgument int value) {
            return 1;
        }

        @Command("integer")
        public static int integerCommand(Integer value) {
            return 1;
        }

        @Command("optional_integer")
        public static int optionalIntegerCommand(@OptionalArgument Integer value) {
            return 1;
        }
    }

    private static class FloatCommands {
        @Command("float")
        public static int floatCommand(float value) {
            return 1;
        }

        @Command("optional_float")
        public static int optionalFloatCommand(@OptionalArgument float value) {
            return 1;
        }

        @Command("float_object")
        public static int floatObjectCommand(Float value) {
            return 1;
        }

        @Command("optional_float_object")
        public static int optionalFloatObjectCommand(@OptionalArgument Float value) {
            return 1;
        }
    }

    private static class DoubleCommands {
        @Command("double")
        public static int doubleCommand(double value) {
            return 1;
        }

        @Command("optional_double")
        public static int optionalDoubleCommand(@OptionalArgument double value) {
            return 1;
        }

        @Command("double_object")
        public static int doubleObjectCommand(Double value) {
            return 1;
        }

        @Command("optional_double_object")
        public static int optionalDoubleObjectCommand(@OptionalArgument Double value) {
            return 1;
        }
    }

}
