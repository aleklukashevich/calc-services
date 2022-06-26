package test;

import com.sun.xml.ws.fault.ServerSOAPFaultException;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import services.dneonline.Calculator;

import java.util.stream.Stream;

public class OneOnlineTests {

    @ParameterizedTest
    @DisplayName("add_{0}_and_{1}")
    @MethodSource("numbers")
    public void addDneOnline(final Object a, final Object b, final Object expResult) {
        val result = new Calculator().getCalculatorSoap().add(a, b);
        Assertions.assertEquals(expResult, result);
    }

    @Test
    @DisplayName("add_MAX_INTEGER_and_1")
    public void addOneOnlineExceptionCheck() {
        Assertions.assertThrows(
                ServerSOAPFaultException.class, () -> new Calculator().getCalculatorSoap().add(Integer.MAX_VALUE, 1)
        );
    }

    @ParameterizedTest
    @DisplayName("add_{0}_and_{1}")
    @MethodSource("throwError")
    public void addOneOnlineExceptionCheck2(final Object a, final Object b) {
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> new Calculator().getCalculatorSoap().add(a, b)
        );
    }

    private static Stream<Arguments> numbers() {
        return Stream.of(
                Arguments.of(1, 2, 3),
                Arguments.of(-1, -3, -4)
        );
    }

    private static Stream<Arguments> throwError() {
        return Stream.of(
                Arguments.of(0.99, 1),
                Arguments.of("a", 2),
                Arguments.of("", "")
        );
    }
}
