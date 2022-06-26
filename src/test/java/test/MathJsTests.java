package test;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import services.MathJsService;

import java.util.stream.Stream;

import static services.Expressions.SUM;
import static utils.Utils.castToClassOrGetString;

public class MathJsTests extends BaseTest {

    @ParameterizedTest
    @DisplayName("add_{0}_and_{1}")
    @MethodSource("numbers")
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addMathJs(final Object a, final Object b, final Object expResult, final Class cls) {
        StringBuilder expression = new StringBuilder();
        expression.append(a).append(SUM.getSymbol()).append(b);
        val res = new MathJsService().add(expression);
        Assertions.assertEquals(expResult, castToClassOrGetString(cls, res));
    }

    private static Stream<Arguments> numbers() {
        return Stream.of(
                Arguments.of(1, 2, 3, Integer.class),
                Arguments.of(Integer.MAX_VALUE, 1, "2.147483648e+9", String.class),
                Arguments.of(-1, -3, -4, Integer.class),
                Arguments.of(0.99, 1, 1.99, Double.class),
                Arguments.of("a", 2, "Error: Undefined symbol a", String.class),
                Arguments.of("", "", "Error: Unexpected end of expression (char 2)", String.class));
    }



}
