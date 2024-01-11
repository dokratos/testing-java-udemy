package org.testing;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

//@Order(1) 
@DisplayName("Test Math")
public class DemoTest {

    Calculator calculator;


//    must be static!!
    @BeforeAll
    static void setup() {
        System.out.println("Executing @BeforeAll");
    }

    //    must be static!!
    @AfterAll
    static void cleanup() {
        System.out.println("Executing @AfterAll");
    }

    @BeforeEach
    void beforeEach() {
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach");
    }

    @AfterEach
    void after() {
        System.out.println("Running after each!");
    }

//    test Method _condition or state change_expected result ::
    @DisplayName("display name annotation")
    @Test
    void testIntegerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo() {
        int result = calculator.integerDivision(4, 2);
        assertEquals(2, result, "4/2 must return 2");
    }

    @Test
    void subtraction() {
//        AAA
//        Arrange
        int minuend = 8;
        int subtrahend = 2;
        int expectedResult = 6;
//        Act
        int result = calculator.integerSubtraction(minuend, subtrahend);
//        Assert

        assertEquals(expectedResult, result, () -> minuend + " minus " + subtrahend + " must return 6");
    }

//    the error message will always be computed, even if the test passes. To avoid it, a lambda can be used!
    @ParameterizedTest
//    @CvsSource({"3,2,1"})
    @CsvFileSource(resources = "/filePath.csv")
//    @MethodSource("integerSubtractionInputParameters")
    void subtractWithParameters(int min, int sub, int res) {
        int result = calculator.integerSubtraction(min, sub);
        assertEquals(res, result, () ->min + " - "+ sub + " must return " + res + "!");
    }

    private static Stream<Arguments> integerSubtractionInputParameters() {
        return Stream.of(
                Arguments.of(3, 2, 1),
                Arguments.of(53, 1, 52),
                Arguments.of(44, 4, 40)
        );
    }

    @DisplayName("division by zero")
//    @Test
    @RepeatedTest(value=3, name="{displayName}. Repetition {currentRepetition} of "+"{totalRepetitions}")
    void testIntegerDivision_WhenDivisionIsByZero_ShouldThrowException(RepetitionInfo repetition, TestInfo info) {
//        Arrange
        int dividend = 4;
        int divisor = 0;
//        Act & Assert
       ArithmeticException arit = assertThrows(ArithmeticException.class, () -> {
//            Act
            calculator.integerDivision(dividend, divisor);
        }, "Division by zero should throw exception");

//       Assert
        assertEquals("/ by zero", arit.getMessage(), " unexpected message");
    }


    @Disabled("The test does not run, but is included in the test report")
    @DisplayName("Fail and disable")
    @Test
    void testFailingMustFail() {
        fail("Running a failing test");
    }

    @ParameterizedTest
    @ValueSource(strings={"John", "Kate", "Alice"})
    void valueSourceDemonstration(String name) {
        System.out.println(name);
        assertNotNull(name);
    }
}
